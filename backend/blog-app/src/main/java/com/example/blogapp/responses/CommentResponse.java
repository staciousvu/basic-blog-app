package com.example.blogapp.responses;

import com.example.blogapp.models.Post;
import com.example.blogapp.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Integer id;
    String content;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("author")
    AuthorResponse authorResponse;
    @JsonProperty("replies")
    List<CommentResponse> replies;

}
