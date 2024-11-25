package com.example.blogapp.responses;

import com.example.blogapp.models.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTreeResponse {

    Integer id;

    String name;

    String description;

    String slug;

    @JsonProperty("is_active")
    Boolean isActive;

    @JsonProperty("subCategories")
    List<CategoryTreeResponse> categories;

}
