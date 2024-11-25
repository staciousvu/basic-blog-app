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
import com.example.blogapp.responses.UploadResponse;
import com.example.blogapp.services.comment.ICommentService;
import com.example.blogapp.utils.FileUploadServiceUtil;
import com.example.blogapp.utils.SlugUtil;
import com.example.blogapp.utils.UploadCloudServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImplV3 implements IPostServiceV3{
    private final PostMapper postMapper;
    private final SlugUtil slugUtil;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadServiceUtil uploadServiceUtil;
    private final UploadCloudServiceUtil uploadCloudServiceUtil;
    private final ICommentService commentService;
    private final TagRepository tagRepository;
    @Override
    public PostResponse createPost(PostDTO postDTO) throws IOException {
        Category category=categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(()->new BlogAppException(ErrorCode.CATEGORY_NOTFOUND));
        User user=userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(()->new BlogAppException(ErrorCode.USER_NOTFOUND));
        List<Tag> tags=postDTO.getTags().stream()
                .map(tagName->tagRepository.findByName(tagName)
                        .orElseGet(()->tagRepository.save(new Tag(null,tagName,null))))
                .toList();

        Post newPost=postMapper.toPost(postDTO);
        if(postDTO.getImage().isEmpty())
            newPost.setImage("default-image.png");
        newPost.setAuthor(user);
        newPost.setCategory(category);
        newPost.setView(0);
//        newPost.setStatus();
        newPost.setSlug(slugUtil.generateSlug(postDTO.getTitle()));
        newPost.setTags(tags);
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

    @Override
    public PostResponse getPost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BlogAppException(ErrorCode.POST_NOTFOUND));
        return PostResponse.PostToResponse(post);
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
    public void updatePost(Integer postId, PostDTO postDTO) throws IOException {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new BlogAppException(ErrorCode.POST_NOTFOUND));
        Category category=categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(()->new BlogAppException(ErrorCode.CATEGORY_NOTFOUND));
        if (postDTO.getTitle() != null && !postDTO.getTitle().equals(post.getTitle()))
            post.setSlug(slugUtil.generateSlug(postDTO.getTitle()));
        postMapper.updateDTOtoPost(postDTO,post);
        post.setCategory(category);
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

    @Override
    public String uploadImageCloudinary(MultipartFile file) throws IOException {
        return uploadCloudServiceUtil.uploadCloudinary(file);
    }

    @Override
    public Page<PostResponse> filter(Integer categoryId, String keyword, String startDateTime, String endDateTime, int page, int limit) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            if (startDateTime != null && !startDateTime.isEmpty()) {
                if (startDateTime.length() == 10) {
                    start = LocalDateTime.parse(startDateTime + " 00:00:00", dateFormatter);
                } else {
                    start = LocalDateTime.parse(startDateTime, dateFormatter);
                }
            }

            if (endDateTime != null && !endDateTime.isEmpty()) {
                if (endDateTime.length() == 10) {
                    end = LocalDateTime.parse(endDateTime + " 23:59:59", dateFormatter);
                } else {
                    end = LocalDateTime.parse(endDateTime, dateFormatter);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm:ss'");
        }

        Pageable pageable = PageRequest.of(page-1, limit);

        Page<Post> postsPage = postRepository.filterPosts(categoryId, keyword, start, end, pageable);
        return postsPage.map(PostResponse::PostToResponse);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new BlogAppException(ErrorCode.POST_NOTFOUND));
        postRepository.delete(post);
    }
}

