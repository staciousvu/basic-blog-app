package com.example.blogapp.services.tag;

import com.example.blogapp.dtos.TagDTO;
import com.example.blogapp.models.Tag;
import com.example.blogapp.projections.TagProjection;
import com.example.blogapp.responses.TagResponse;

import java.util.List;

public interface ITagService {
    Tag createTag(TagDTO tagDTO);
    List<TagProjection> getAllTags();
    TagResponse getTagWithPosts(Integer tagId);
    void deleteTag(Integer tagId);
}
