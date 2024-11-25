package com.example.blogapp.mappers;

import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.models.Post;
import com.example.blogapp.responses.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    Post toPost(PostDTO postDTO);
    void updateDTOtoPost(PostDTO postDTO, @MappingTarget Post post);
    PostResponse toPostResponse(Post post);

}
