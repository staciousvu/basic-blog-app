package com.example.blogapp.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin")
    ;
    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}
