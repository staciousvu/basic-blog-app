package com.example.blogapp.services.post;

import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.enums.PostStatus;
import com.example.blogapp.exceptions.BlogAppException;
import com.example.blogapp.exceptions.ErrorCode;
import com.example.blogapp.mappers.PostMapper;
import com.example.blogapp.models.Category;
import com.example.blogapp.models.Post;
import com.example.blogapp.models.Tag;
import com.example.blogapp.models.User;
import com.example.blogapp.repositories.CategoryRepository;
import com.example.blogapp.repositories.PostRepository;
import com.example.blogapp.repositories.TagRepository;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.responses.PostResponse;
import com.example.blogapp.services.comment.ICommentService;
import com.example.blogapp.utils.FileUploadServiceUtil;
import com.example.blogapp.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostServiceImplV2 implements IPostServiceV2{
    private final PostMapper postMapper;
    private final SlugUtil slugUtil;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadServiceUtil uploadServiceUtil;
    private final ICommentService commentService;
    private final TagRepository tagRepository;
    @Override
    public PostResponse createPost(PostDTO postDTO, MultipartFile file) throws IOException {
        Category category=categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(()->new BlogAppException(ErrorCode.CATEGORY_NOTFOUND));
        User user=userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(()->new BlogAppException(ErrorCode.USER_NOTFOUND));
        Post newPost=postMapper.toPost(postDTO);
        if (!file.isEmpty()){
            String fileName= uploadServiceUtil.storeFile(file,"post");
            newPost.setImage(fileName);
        }else {
            newPost.setImage("default-post.png");
        }
        newPost.setAuthor(user);
        newPost.setCategory(category);
        newPost.setView(0);
        newPost.setStatus("PUBLIC");
        newPost.setSlug(slugUtil.generateSlug(postDTO.getTitle()));
        return PostResponse.PostToResponse(postRepository.save(newPost));
    }

    @Override
    public Page<PostResponse> getPostsCategory(Integer categoryId, int page, int limit) {
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.findAllByCategoryId(categoryId,pageRequest).map(PostResponse::PostToResponse);
    }

    @Override
    public Page<PostResponse> getPostsAuthor(Integer authorId, int page, int limit) {
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.findAllByAuthorId(authorId,pageRequest).map(PostResponse::PostToResponse);
    }

    @Override
    public PostResponse getPostSLug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new BlogAppException(ErrorCode.POST_NOTFOUND));
        viewCount(post);
        postRepository.save(post);
        PostResponse postResponse=PostResponse.PostToResponse(post);
        postResponse.setCommentResponses(commentService.getCommentsByPost(post.getId()));
        return postResponse;
    }
    private void viewCount(Post post){
        post.setView(post.getView()+1);
    }

    @Override
    public Page<PostResponse> getAllPost(int page, int limit) {
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.findAll(pageRequest).map(PostResponse::PostToResponse);
    }

    @Override
    public void updatePost(Integer postId, PostDTO postDTO,MultipartFile file) throws IOException {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new BlogAppException(ErrorCode.POST_NOTFOUND));
        if (postDTO.getTitle() != null && !postDTO.getTitle().equals(post.getTitle()))
            post.setSlug(slugUtil.generateSlug(postDTO.getTitle()));
        postMapper.updateDTOtoPost(postDTO,post);
        if (file != null && !file.isEmpty()) {
            String fileName = uploadServiceUtil.storeFile(file, "post");
            if (post.getImage()!=null)
                uploadServiceUtil.deleteFile(post.getImage(),"post");
            post.setImage(fileName);
        }
        postRepository.save(post);
    }

    @Override
    public void hidePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new BlogAppException(ErrorCode.POST_NOTFOUND));
        post.setStatus(PostStatus.PRIVATE.getName());
        postRepository.save(post);
    }

    @Override
    public void publishPost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new BlogAppException(ErrorCode.POST_NOTFOUND));
        post.setStatus(PostStatus.PUBLIC.getName());
        postRepository.save(post);
    }

    @Override
    public Page<PostResponse> findByStatus(String status, int page, int limit) {
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.findAllByStatus(status,pageRequest).map(PostResponse::PostToResponse);
    }

    @Override
    public Page<PostResponse> searchPosts(String keyword, int page, int limit) {
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.searchByTitle(keyword,pageRequest).map(PostResponse::PostToResponse);
    }

    @Override
    public Page<PostResponse> getPostsTag(Integer tagId, int page, int limit) {
        Tag tag=tagRepository.findById(tagId)
                .orElseThrow(()->new BlogAppException(ErrorCode.TAG_NOTFOUND));
        PageRequest pageRequest=PageRequest.of(page-1,limit);
        return postRepository.findByTagID(tagId,pageRequest).map(PostResponse::PostToResponse);
    }
}
