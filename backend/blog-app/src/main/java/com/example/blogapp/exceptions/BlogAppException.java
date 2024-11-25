package com.example.blogapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogAppException extends RuntimeException {
    public BlogAppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
    private ErrorCode errorCode;
}
