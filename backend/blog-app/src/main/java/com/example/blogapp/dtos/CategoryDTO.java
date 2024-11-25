package com.example.blogapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Integer id;

    @NotBlank(message = "TÊN DANH MỤC KHÔNG ĐƯỢC ĐỂ TRỐNG")
    private String name;

    private String description;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("is_active")
    private Boolean isActive;

}
