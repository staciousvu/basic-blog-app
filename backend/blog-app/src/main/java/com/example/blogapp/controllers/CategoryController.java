package com.example.blogapp.controllers;

import com.example.blogapp.dtos.CategoryDTO;
import com.example.blogapp.models.Category;
import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.CategoryTreeResponse;
import com.example.blogapp.services.category.ICategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    @PostMapping("")
    public ApiResponse<Category> create(@Valid @RequestBody CategoryDTO categoryDTO){
        return ApiResponse.<Category>builder()
                .data(categoryService.createCategory(categoryDTO))
                .build();
    }
    @GetMapping("/{slug}")
    public ApiResponse<Category> get(@PathVariable String slug){
        return ApiResponse.<Category>builder()
                .data(categoryService.getBySlug(slug))
                .build();
    }
    @GetMapping("")
    public ApiResponse<List<Category>> getAllCategory(){
        return ApiResponse.<List<Category>>builder()
                .data(categoryService.getAll())
                .build();
    }

    @GetMapping("/tree")
    public ApiResponse<List<CategoryTreeResponse>> getAllCategoryTree(){
        return ApiResponse.<List<CategoryTreeResponse>>builder()
                .data(categoryService.categoryTreeStructure())
                .build();
    }

    @PostMapping("/{id}")
    public ApiResponse<Category> update(@PathVariable Integer id,@Valid @RequestBody CategoryDTO categoryDTO){
        return ApiResponse.<Category>builder()
                .data(categoryService.updateCategory(id,categoryDTO))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Min(1) Integer id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
