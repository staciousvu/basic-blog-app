package com.example.blogapp.controllers;
import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.PageResponse;
import com.example.blogapp.responses.PostResponse;
import com.example.blogapp.services.post.PostServiceImplV2;
import com.example.blogapp.services.post.PostServiceImplV3;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostControllerV3 {
    private final PostServiceImplV3 postService;

    // Tạo bài viết
    @PostMapping(value = "")
    public ApiResponse<PostResponse> createPost(@Valid @RequestBody PostDTO postDTO) throws IOException {
        return ApiResponse.<PostResponse>builder()
                .message("Create post success.")
                .data(postService.createPost(postDTO))
                .build();
    }

    // Lấy tất cả bài viết với phân trang
    @GetMapping("")
    public ApiResponse<PageResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage = postService.getAllPost(page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts")
                .data(convert(postResponsePage))
                .build();
    }
    @GetMapping("/tag/{tagId}")
    public ApiResponse<PageResponse<PostResponse>> getAllPostsTag(
            @PathVariable @Min(1) Integer tagId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage = postService.getPostsTag(tagId,page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts by tag")
                .data(convert(postResponsePage))
                .build();
    }

    // Lấy bài viết theo tác giả với phân trang
    @GetMapping("/author/{authorId}")
    public ApiResponse<PageResponse<PostResponse>> getAllPostsAuthor(
            @PathVariable @Min(1) Integer authorId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage = postService.getPostsAuthor(authorId, page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts by author")
                .data(convert(postResponsePage))
                .build();
    }

    // Lấy bài viết theo danh mục với phân trang
    @GetMapping("/category/{categoryId}")
    public ApiResponse<PageResponse<PostResponse>> getAllPostsCategory(
            @PathVariable @Min(1) Integer categoryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage=postService.getPostsCategory(categoryId,page,limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts by category")
                .data(convert(postResponsePage))
                .build();
    }

    // Lấy bài viết theo slug
    @GetMapping("/slug/{slug}")
    public ApiResponse<PostResponse> getPostSlug(@PathVariable String slug) {
        return ApiResponse.<PostResponse>builder()
                .message("This is all posts by slug")
                .data(postService.getPostSLug(slug))
                .build();
    }
    //post by id
    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Integer postId) {
        return ApiResponse.<PostResponse>builder()
                .message("This is post by postid")
                .data(postService.getPost(postId))
                .build();
    }
    // Ẩn bài viết
    @PutMapping("/{postId}/hide")
    public ApiResponse<Void> hidePost(@PathVariable @Min(1) Integer postId) {
        postService.hidePost(postId);
        return ApiResponse.<Void>builder()
                .message("Post hidden successfully")
                .build();
    }

    // Đăng bài viết
    @PutMapping("/{postId}/publish")
    public ApiResponse<Void> publishPost(@PathVariable @Min(1) Integer postId) {
        postService.publishPost(postId);
        return ApiResponse.<Void>builder()
                .message("Post published successfully")
                .build();
    }

    // Tìm kiếm bài viết theo từ khóa với phân trang
    @GetMapping("/search")
    public ApiResponse<PageResponse<PostResponse>> searchPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage = postService.searchPosts(keyword, page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts by keyword")
                .data(convert(postResponsePage))
                .build();
    }


    // Lọc bài viết theo trạng thái với phân trang
    @GetMapping("/status/{status}")
    public ApiResponse<PageResponse<PostResponse>> findByStatus(
            @PathVariable String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<PostResponse> postResponsePage = postService.findByStatus(status, page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts by status")
                .data(convert(postResponsePage))
                .build();
    }
    @GetMapping("/filter")
    public ApiResponse<PageResponse<PostResponse>> filterPosts(
            @RequestParam(value = "categoryId") Integer categoryId,
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "startDateTime") String startDateTime,
            @RequestParam(value = "endDateTime") String endDateTime,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ){
        Page<PostResponse> postResponsePage = postService.filter(categoryId, keyword, startDateTime, endDateTime, page, limit);
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .message("This is all posts filter")
                .data(convert(postResponsePage))
                .build();
    }


    // Cập nhật bài viết
    @PutMapping("/update/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable @Min(1) Integer postId,
                                                @Valid @RequestBody PostDTO postDTO) throws IOException {
        postService.updatePost(postId, postDTO);
        return ApiResponse.<PostResponse>builder()
                .message("Post updated successfully")
                .build();
    }
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable @Min(1) Integer postId) {
        postService.deletePost(postId);
        return ApiResponse.<Void>builder().message("Post deleted successfully").build();
    }

    // Mapping helper methods
    private PageResponse<PostResponse> convert(Page<PostResponse> postResponsePage){
        return PageResponse.<PostResponse>builder()
                .content(postResponsePage.getContent())
                .size(postResponsePage.getSize())
                .currentPage(postResponsePage.getNumber())
                .totalElements(postResponsePage.getTotalElements())
                .totalPages(postResponsePage.getTotalPages())
                .build();
    }
}
