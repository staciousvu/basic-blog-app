package com.example.blogapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDTO {
    @NotBlank(message = "Username can not empty")
    String email;
    @NotBlank(message = "Password can not empty")
    String password;
}
