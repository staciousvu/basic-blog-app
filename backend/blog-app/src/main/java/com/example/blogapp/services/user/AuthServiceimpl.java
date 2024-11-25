package com.example.blogapp.services.user;

import com.example.blogapp.dtos.UserCreateDTO;
import com.example.blogapp.dtos.UserLoginDTO;
import com.example.blogapp.exceptions.BlogAppException;
import com.example.blogapp.exceptions.ErrorCode;
import com.example.blogapp.models.User;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.responses.AuthResponse;
import com.example.blogapp.responses.AuthorResponse;
import com.example.blogapp.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements IAuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @Override
    public User register(UserCreateDTO userCreateDTO) {
        Optional<User> user=userRepository.findByEmail(userCreateDTO.getEmail());
        if (user.isPresent())
            throw new BlogAppException(ErrorCode.EMAIL_EXISTED);
        User add_user=User.builder()
                .name(userCreateDTO.getName())
                .avatar(userCreateDTO.getAvatar())
                .email(userCreateDTO.getEmail())
                .role("USER")
                .status("INACTIVE")
                .password(passwordEncoder.encode(userCreateDTO.getPassword()))
                .build();
        return userRepository.save(add_user);
    }

    @Override
    public AuthResponse login(UserLoginDTO userLoginDTO) {
        User user=userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(()->new BlogAppException(ErrorCode.USER_NOTFOUND));
        System.out.println(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getEmail(), userLoginDTO.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);
        return AuthResponse.builder()
                .token(jwtUtils.generateToken(user))
                .user(AuthorResponse.builder()
                        .name(user.getName())
                        .avatar(user.getAvatar())
                        .id(user.getId())
                        .build())
                .build();
    }
}
