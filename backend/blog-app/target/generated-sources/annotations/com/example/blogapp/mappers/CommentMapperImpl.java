package com.example.blogapp.mappers;

import com.example.blogapp.dtos.CommentDTO;
import com.example.blogapp.models.Comment;
import com.example.blogapp.responses.CommentResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T06:17:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.id( commentDTO.getId() );
        comment.content( commentDTO.getContent() );
        comment.parentId( commentDTO.getParentId() );

        return comment.build();
    }

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.id( comment.getId() );
        commentResponse.content( comment.getContent() );
        commentResponse.createdAt( comment.getCreatedAt() );

        return commentResponse.build();
    }
}
