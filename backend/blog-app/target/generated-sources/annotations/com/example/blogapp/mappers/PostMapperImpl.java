package com.example.blogapp.mappers;

import com.example.blogapp.dtos.PostDTO;
import com.example.blogapp.models.Post;
import com.example.blogapp.responses.PostResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T15:05:18+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.id( postDTO.getId() );
        post.content( postDTO.getContent() );
        post.title( postDTO.getTitle() );
        post.image( postDTO.getImage() );
        post.status( postDTO.getStatus() );

        return post.build();
    }

    @Override
    public void updateDTOtoPost(PostDTO postDTO, Post post) {
        if ( postDTO == null ) {
            return;
        }

        if ( postDTO.getId() != null ) {
            post.setId( postDTO.getId() );
        }
        if ( postDTO.getContent() != null ) {
            post.setContent( postDTO.getContent() );
        }
        if ( postDTO.getTitle() != null ) {
            post.setTitle( postDTO.getTitle() );
        }
        if ( postDTO.getImage() != null ) {
            post.setImage( postDTO.getImage() );
        }
        if ( postDTO.getStatus() != null ) {
            post.setStatus( postDTO.getStatus() );
        }
    }

    @Override
    public PostResponse toPostResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.id( post.getId() );
        postResponse.content( post.getContent() );
        postResponse.title( post.getTitle() );
        postResponse.image( post.getImage() );
        postResponse.updatedAt( post.getUpdatedAt() );
        postResponse.createdAt( post.getCreatedAt() );
        postResponse.view( post.getView() );
        postResponse.slug( post.getSlug() );
        postResponse.status( post.getStatus() );

        return postResponse.build();
    }
}
