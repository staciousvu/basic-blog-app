package com.example.blogapp.repositories;

import com.example.blogapp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Optional<Post> findById(Integer id);
    Page<Post> findAllByCategoryId(Integer id, Pageable pageable);
    Page<Post> findAllByAuthorId(Integer id, Pageable pageable);
    Optional<Post> findBySlug(String slug);
    Page<Post> findAllByStatus(String status, Pageable pageable);
    @Query("select p from Post p where LOWER(p.title) like LOWER(CONCAT('%', :keyword, '%'))")
    Page<Post> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    @Query("select p from Post p join p.tags t where t.id=:tagId")
    Page<Post> findByTagID(@Param("tagId") Integer tagId,Pageable pageable);

    @Query("SELECT p FROM Post p WHERE " +
            "(:categoryId = 0 OR :categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:startDateTime IS NULL OR p.createdAt >= :startDateTime) AND " +
            "(:endDateTime IS NULL OR p.createdAt <= :endDateTime)")
    Page<Post> filterPosts(Integer categoryId, String keyword, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);



}
