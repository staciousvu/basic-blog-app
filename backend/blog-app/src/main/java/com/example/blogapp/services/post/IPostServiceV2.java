package com.example.blogapp.services.post;

import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.models.Post;
import com.example.blogapp.responses.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPostServiceV2 {
    PostResponse createPost(PostDTO postDTO, MultipartFile file) throws IOException;
    Page<PostResponse> getPostsCategory(Integer categoryId, int page, int limit);
    Page<PostResponse> getPostsAuthor(Integer authorId, int page, int limit);
    PostResponse getPostSLug(String slug);
    Page<PostResponse> getAllPost(int page, int limit);
    void updatePost(Integer postId, PostDTO postDTO, MultipartFile file) throws IOException;
    void hidePost(Integer postId);
    void publishPost(Integer postId);
    Page<PostResponse> findByStatus(String status, int page, int limit);
    Page<PostResponse> searchPosts(String keyword, int page, int limit);
    Page<PostResponse> getPostsTag(Integer tagId, int page, int limit);
}
