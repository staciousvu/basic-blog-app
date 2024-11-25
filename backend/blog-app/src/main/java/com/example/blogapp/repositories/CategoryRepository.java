package com.example.blogapp.repositories;

import com.example.blogapp.models.Category;
import com.example.blogapp.responses.CategoryTreeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsById(Integer id);
    Boolean existsBySlug(String slug);
    Optional<Category> findBySlug(String name);
    Boolean existsByName(String name);
    List<Category> findByParentIdIsNull();
    List<Category> findByParentId(Integer id);
    List<Category> findAllByIsActiveTrue();
}
