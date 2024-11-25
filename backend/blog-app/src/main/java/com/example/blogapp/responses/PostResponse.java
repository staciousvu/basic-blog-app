package com.example.blogapp.responses;

import com.example.blogapp.models.Comment;
import com.example.blogapp.models.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Integer id;
    String content;
    String title;
    String image;
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    Integer view;
    String slug;
    String status;
    @JsonProperty("category")
    CategoryResponse categoryResponse;
    @JsonProperty("author")
    AuthorResponse authorResponse;
    @JsonProperty("tags")
    List<TagResponse> tagResponses;
    @JsonProperty("comments")
    List<CommentResponse> commentResponses;
    public static PostResponse PostToResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .status(post.getStatus())
                .view(post.getView())
                .slug(post.getSlug())
                .categoryResponse(CategoryResponse.builder()
                        .id(post.getCategory().getId())
                        .name(post.getCategory().getName())
                        .build())
                .authorResponse(AuthorResponse.builder()
                        .id(post.getAuthor().getId())
                        .name(post.getAuthor().getName())
                        .avatar(post.getAuthor().getAvatar())
                        .build())
                .tagResponses(post.getTags() == null ?
                        List.of() :
                        post.getTags().stream()
                                .map(tag -> TagResponse.builder()
                                        .id(tag.getId())
                                        .name(tag.getName())
                                        .build())
                                .toList())

//                .commentResponses(post.getComments())
                .build();
    }
}
