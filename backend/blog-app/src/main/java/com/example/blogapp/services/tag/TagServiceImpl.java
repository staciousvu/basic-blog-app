package com.example.blogapp.services.tag;

import com.example.blogapp.dtos.TagDTO;
import com.example.blogapp.exceptions.BlogAppException;
import com.example.blogapp.exceptions.ErrorCode;
import com.example.blogapp.models.Post;
import com.example.blogapp.models.Tag;
import com.example.blogapp.projections.TagProjection;
import com.example.blogapp.repositories.TagRepository;
import com.example.blogapp.responses.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService{
    private final TagRepository tagRepository;
    @Override
    public Tag createTag(TagDTO tagDTO) {
        Optional<Tag> tag=tagRepository.findByName(tagDTO.getName());
        if (tag.isPresent())
            throw new BlogAppException(ErrorCode.TAG_EXISTED);

        return tagRepository.save(Tag.builder()
                        .name(tagDTO.getName())
                .build());
    }

    @Override
    public List<TagProjection> getAllTags() {
        return tagRepository.findAllTagsWithoutPosts();
    }

    @Override
    public TagResponse getTagWithPosts(Integer tagId) {
        Tag tag=tagRepository.findById(tagId).orElseThrow(()->new BlogAppException(ErrorCode.TAG_NOTFOUND));
        return TagResponse.TagToResponse(tag);
    }

    @Override
    public void deleteTag(Integer tagId) {
        Tag tag=tagRepository.findById(tagId).orElseThrow(()->new BlogAppException(ErrorCode.TAG_NOTFOUND));
        for (Post post:tag.getPosts())
            post.getTags().remove(tag);
        tag.getPosts().clear();
        tagRepository.save(tag);
        tagRepository.delete(tag);
    }
}
