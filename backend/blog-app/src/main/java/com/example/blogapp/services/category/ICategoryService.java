package com.example.blogapp.services.category;

import com.example.blogapp.dtos.CategoryDTO;
import com.example.blogapp.models.Category;
import com.example.blogapp.responses.CategoryTreeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Integer id,CategoryDTO categoryDTO);
    List<Category> getAll();
    Category getBySlug(String slug);
    void delete(Integer id);
    List<CategoryTreeResponse> categoryTreeStructure();
}
