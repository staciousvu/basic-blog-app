package com.example.blogapp.controllers;

import com.example.blogapp.dtos.TagDTO;
import com.example.blogapp.models.Tag;
import com.example.blogapp.projections.TagProjection;
import com.example.blogapp.repositories.TagRepository;
import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.PostResponse;
import com.example.blogapp.responses.TagResponse;
import com.example.blogapp.services.tag.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final ITagService tagService;
    @GetMapping("")
    public ApiResponse<List<TagProjection>> getAllTag(){
        return ApiResponse.<List<TagProjection>>builder()
                .message("all tags.")
                .data(tagService.getAllTags())
                .build();
    }
    @PostMapping("")
    public ApiResponse<Tag> createTag(@RequestBody TagDTO tagDTO){
        return ApiResponse.<Tag>builder()
                .message("Create tag successful.")
                .data(tagService.createTag(tagDTO))
                .build();
    }
    @GetMapping("/{tagId}")
    public ApiResponse<TagResponse> getTag(@PathVariable Integer tagId) {
        return ApiResponse.<TagResponse>builder()
                .message("get tag with detail in4.")
                .data(tagService.getTagWithPosts(tagId))
                .build();
    }
    @DeleteMapping("/{tagId}")
    public ApiResponse<Void> deleteTag(@PathVariable Integer tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.<Void>builder()
                .message("Delete tag successful.")
                .build();
    }
}
