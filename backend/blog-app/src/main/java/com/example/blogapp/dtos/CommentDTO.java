package com.example.blogapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDTO {
    Integer id;

    @NotBlank(message = "Content can not blank")
    String content;
    @NotNull(message = "Author not null")
    @JsonProperty("author_id")
    Integer authorId;
    @NotNull(message = "Post not null")
    @JsonProperty("post_id")
    Integer postId;
    @JsonProperty("parent_id")
    Integer parentId;
}
