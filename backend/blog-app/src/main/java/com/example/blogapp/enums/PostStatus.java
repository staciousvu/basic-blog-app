package com.example.blogapp.enums;

import lombok.Getter;

@Getter
public enum PostStatus {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE")
    ;
    private final String name;

    PostStatus(String name) {
        this.name = name;
    }
}
