package com.example.blogapp.repositories;

import com.example.blogapp.models.Tag;
import com.example.blogapp.projections.TagProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
    Optional<Tag> findByName(String name);
    @Query("SELECT t.id AS id, t.name AS name FROM Tag t")
    List<TagProjection> findAllTagsWithoutPosts();
}


