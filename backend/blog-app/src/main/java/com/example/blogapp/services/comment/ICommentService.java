package com.example.blogapp.services.comment;

import com.example.blogapp.dtos.CommentDTO;
import com.example.blogapp.models.Comment;
import com.example.blogapp.responses.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(CommentDTO commentDTO);
    List<CommentResponse> getCommentsByPost(Integer postId);
}
