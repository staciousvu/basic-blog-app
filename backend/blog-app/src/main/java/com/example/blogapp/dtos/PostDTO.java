package com.example.blogapp.dtos;

import com.example.blogapp.enums.PostStatus;
import com.example.blogapp.models.Tag;
import com.example.blogapp.validator.EnumPattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDTO {
        Integer id;

        @NotBlank(message = "NỘI DUNG KHÔNG ĐỂ TRỐNG")
        String content;

        @NotBlank(message = "TIÊU ĐỀ KHÔNG ĐỂ TRỐNG")
        String title;

        @JsonProperty("image")
        String image;

        @EnumPattern(name = "TRẠNG THÁI",value = "PUBLIC|PRIVATE",enumClass = PostStatus.class)
        String status;

        @NotNull(message = "THIẾU ID DANH MỤC")
        @JsonProperty("category_id")
        Integer categoryId;

        @NotNull(message = "THIẾU ID TÁC GIẢ")
        @JsonProperty("author_id")
        Integer authorId;

        @JsonProperty("tags")
        List<String> tags;
}
