package com.example.blogapp.mappers;

import com.example.blogapp.dtos.CategoryDTO;
import com.example.blogapp.models.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T06:17:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryDTO.getId() );
        category.name( categoryDTO.getName() );
        category.description( categoryDTO.getDescription() );
        category.parentId( categoryDTO.getParentId() );
        category.isActive( categoryDTO.getIsActive() );

        return category.build();
    }

    @Override
    public void updateDtoToCategory(CategoryDTO categoryDTO, Category category) {
        if ( categoryDTO == null ) {
            return;
        }

        if ( categoryDTO.getId() != null ) {
            category.setId( categoryDTO.getId() );
        }
        if ( categoryDTO.getName() != null ) {
            category.setName( categoryDTO.getName() );
        }
        if ( categoryDTO.getDescription() != null ) {
            category.setDescription( categoryDTO.getDescription() );
        }
        if ( categoryDTO.getParentId() != null ) {
            category.setParentId( categoryDTO.getParentId() );
        }
        if ( categoryDTO.getIsActive() != null ) {
            category.setIsActive( categoryDTO.getIsActive() );
        }
    }
}
