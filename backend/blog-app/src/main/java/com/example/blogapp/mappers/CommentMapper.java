package com.example.blogapp.mappers;

import com.example.blogapp.dtos.CommentDTO;
import com.example.blogapp.models.Comment;
import com.example.blogapp.responses.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    Comment toComment(CommentDTO commentDTO);
    CommentResponse toCommentResponse(Comment comment);

}
