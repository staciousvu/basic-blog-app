package com.example.blogapp.repositories;

import com.example.blogapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsById(Integer id);
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);

}
