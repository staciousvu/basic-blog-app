package com.example.blogapp.dtos;

import com.example.blogapp.enums.UserRole;
import com.example.blogapp.enums.UserStatus;
import com.example.blogapp.validator.EnumPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDTO {
    @NotBlank(message = "KHÔNG ĐƯỢC ĐỂ TRỐNG TÊN")
    String name;

    @NotBlank(message = "KHÔNG ĐƯỢC ĐỂ TRỐNG EMAIL")
    @Email(message = "KHÔNG ĐÚNG ĐỊNH DẠNG EMAIL")
    String email;

    @NotBlank(message = "KHÔNG ĐƯỢC ĐỂ TRỐNG MẬT KHẨU")
    @Size(min = 6,message = "MẬT KHẨU TỐI THIẾU 6 KÍ TỰ")
    String password;
    String avatar;
    @EnumPattern(name = "ROLE",value = "USER|ADMIN",enumClass = UserRole.class)
    String role;
    @EnumPattern(name = "STATUS",value = "ACTIVE|INACTIVE|BLOCK",enumClass =UserStatus.class)
    String status;
}
