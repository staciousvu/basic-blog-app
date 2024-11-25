package com.example.blogapp.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCK("block"),
    ;
    private final String name;

    UserStatus(String name) {
        this.name = name;
    }
}
