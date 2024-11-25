package com.example.blogapp.controllers;

import com.example.blogapp.dtos.UserCreateDTO;
import com.example.blogapp.dtos.UserLoginDTO;
import com.example.blogapp.models.User;
import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.AuthResponse;
import com.example.blogapp.services.user.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IAuthService authService;
    @PostMapping("")
    public String add(@Valid @RequestBody UserCreateDTO userCreateDTO){
        return "add users";
    }
    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody UserCreateDTO userCreateDTO){
        return ApiResponse.<User>builder()
                .data(authService.register(userCreateDTO))
                .message("Create user successfully")
                .build();
    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody UserLoginDTO userLoginDTO){
        return ApiResponse.<AuthResponse>builder()
                .data(authService.login(userLoginDTO))
                .message("Login successfully")
                .build();
    }
}
