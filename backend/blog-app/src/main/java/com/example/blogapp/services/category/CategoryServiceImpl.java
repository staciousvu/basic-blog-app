package com.example.blogapp.services.category;

import com.example.blogapp.dtos.CategoryDTO;
import com.example.blogapp.exceptions.BlogAppException;
import com.example.blogapp.exceptions.ErrorCode;
import com.example.blogapp.mappers.CategoryMapper;
import com.example.blogapp.models.Category;
import com.example.blogapp.repositories.CategoryRepository;
import com.example.blogapp.responses.CategoryTreeResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    public String generateSlug(String name){
        String slug = name.toLowerCase().replaceAll("đ","d");
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        slug = slug.replaceAll("[^a-z0-9\\s]", "");
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("^-+", "").replaceAll("-+$", "");
        String originalSlug = slug;
        int count = 1;
        while (categoryRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + count;
            count++;
        }
        return slug;
    }
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName()))
            throw new BlogAppException(ErrorCode.CATEGORY_NAME_EXISTED);
        Category category=mapper.toCategory(categoryDTO);
        category.setSlug(generateSlug(categoryDTO.getName()));
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> category=categoryRepository.findById(id);
        if (category.isPresent()){
            Category newCategory=category.get();
            mapper.updateDtoToCategory(categoryDTO,newCategory);
            newCategory.setSlug(generateSlug(categoryDTO.getName()));
            return categoryRepository.save(newCategory);
        }
        throw new BlogAppException(ErrorCode.CATEGORY_NOTFOUND);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByIsActiveTrue();
    }

    @Override
    public Category getBySlug(String slug) {
        Optional<Category> category=categoryRepository.findBySlug(slug);
        if (category.isEmpty())
            throw new BlogAppException(ErrorCode.CATEGORY_NOTFOUND);
        return category.get();
    }

    @Override
    public void delete(Integer id) {
        Category category=categoryRepository.findById(id)
                .orElseThrow(()->new BlogAppException(ErrorCode.CATEGORY_NOTFOUND));
        List<Category> batchDelete=new ArrayList<>();
        batchDelete.add(category);
        deleteSubCategories(category,batchDelete);

        for (Category cat:batchDelete)
            cat.setIsActive(false);

        categoryRepository.saveAll(batchDelete);
    }
    private void deleteSubCategories(Category category,List<Category> batchDelete){
        List<Category> subCategories = categoryRepository.findByParentId(category.getId());
        for (Category subCategory : subCategories) {
            batchDelete.add(subCategory);
            deleteSubCategories(subCategory,batchDelete);
        }
    }

    @Override
    public List<CategoryTreeResponse> categoryTreeStructure() {
        List<Category> categoriesRoot=categoryRepository.findByParentIdIsNull();
        return categoriesRoot.stream().map(
                this::buildCategoryTree
        ).collect(Collectors.toList());
    }
    private CategoryTreeResponse buildCategoryTree(Category category){
        List<Category> subCategories=categoryRepository.findByParentId(category.getId());
        return CategoryTreeResponse.builder()
                .id(category.getId())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .name(category.getName())
                .slug(category.getSlug())
                .categories(subCategories.stream().map(
                        this::buildCategoryTree
                ).collect(Collectors.toList()))
                .build();
    }
}
