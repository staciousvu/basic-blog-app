package com.example.blogapp.responses;

import com.example.blogapp.models.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagResponse {
    Integer id;
    String name;
    @JsonProperty("posts")
    List<PostResponse> postResponses;
    public static TagResponse TagToResponse(Tag tag){
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .postResponses(tag.getPosts().stream().map(PostResponse::PostToResponse).collect(Collectors.toList()))
                .build();
    }
}
