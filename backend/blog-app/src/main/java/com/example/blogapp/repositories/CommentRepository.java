package com.example.blogapp.repositories;

import com.example.blogapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByPostIdAndParentId(Integer postId,Integer parentId);
}
