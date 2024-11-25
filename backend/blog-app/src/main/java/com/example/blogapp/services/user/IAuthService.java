package com.example.blogapp.services.user;

import com.example.blogapp.dtos.UserCreateDTO;
import com.example.blogapp.dtos.UserLoginDTO;
import com.example.blogapp.models.User;
import com.example.blogapp.responses.AuthResponse;
import com.example.blogapp.responses.AuthorResponse;

public interface IAuthService {
    public User register(UserCreateDTO userCreateDTO);
    public AuthResponse login(UserLoginDTO userLoginDTO);
}
