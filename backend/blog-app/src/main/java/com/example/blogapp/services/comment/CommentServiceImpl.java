package com.example.blogapp.services.comment;

import com.example.blogapp.dtos.CommentDTO;
import com.example.blogapp.exceptions.BlogAppException;
import com.example.blogapp.exceptions.ErrorCode;
import com.example.blogapp.mappers.CommentMapper;
import com.example.blogapp.models.Comment;
import com.example.blogapp.models.Post;
import com.example.blogapp.models.User;
import com.example.blogapp.repositories.CommentRepository;
import com.example.blogapp.repositories.PostRepository;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.responses.AuthorResponse;
import com.example.blogapp.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public CommentResponse createComment(CommentDTO commentDTO) {
        User user=userRepository.findById(commentDTO.getAuthorId()).orElseThrow(()->
                new BlogAppException(ErrorCode.USER_NOTFOUND));
        Post post=postRepository.findById(commentDTO.getPostId()).orElseThrow(()->
                new BlogAppException(ErrorCode.POST_NOTFOUND));
        Comment comment=commentMapper.toComment(commentDTO);
        comment.setAuthor(user);
        comment.setPost(post);

        CommentResponse commentResponse=commentMapper.toCommentResponse(commentRepository.save(comment));
        commentResponse.setAuthorResponse(AuthorResponse.builder()
                        .id(user.getId())
                        .avatar(user.getAvatar())
                        .name(user.getName())
                .build());
        return commentResponse;
    }

    @Override
    public List<CommentResponse> getCommentsByPost(Integer postId) {
        return buildTreeStructureComment(postId,null);
    }
    private List<CommentResponse> buildTreeStructureComment(Integer postId,Integer parentId){
        List<Comment> comments=commentRepository.findByPostIdAndParentId(postId,parentId);
        List<CommentResponse> commentResponses=new ArrayList<>();
        for (Comment comment:comments){
            CommentResponse commentResponse=commentMapper.toCommentResponse(comment);
            commentResponse.setAuthorResponse(AuthorResponse.builder()
                    .id(comment.getAuthor().getId())
                    .avatar(comment.getAuthor().getAvatar())
                    .name(comment.getAuthor().getName())
                    .build());
            commentResponse.setReplies(buildTreeStructureComment(postId,comment.getId()));
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

}
