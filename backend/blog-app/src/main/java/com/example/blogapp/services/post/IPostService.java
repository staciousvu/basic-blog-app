package com.example.blogapp.services.post;

import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    Post createPost(PostDTO postDTO, MultipartFile file) throws IOException;
    Page<Post> getPostsCategory(Integer categoryId, int page, int limit);
    Page<Post> getPostsAuthor(Integer authorId, int page, int limit);
    Post getPostSLug(String slug);
    Page<Post> getAllPost(int page, int limit);
    void updatePost(Integer postId, PostDTO postDTO, MultipartFile file) throws IOException;
    void hidePost(Integer postId);
    void publishPost(Integer postId);
    Page<Post> findByStatus(String status, int page, int limit);
    Page<Post> searchPosts(String keyword, int page, int limit);
}
