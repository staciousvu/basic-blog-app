package com.example.blogapp.mappers;

import com.example.blogapp.dtos.CategoryDTO;
import com.example.blogapp.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    Category toCategory(CategoryDTO categoryDTO);
    void updateDtoToCategory(CategoryDTO categoryDTO, @MappingTarget Category category);
}
