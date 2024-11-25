package com.example.blogapp.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    CATEGORY_NOTFOUND(HttpStatus.NOT_FOUND.value(),"KHÔNG TÌM THẤY DANH MỤC", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_EXISTED(HttpStatus.CONFLICT.value(),"TÊN DANH MỤC ĐÃ TỒN TẠI",HttpStatus.CONFLICT),
    USER_NOTFOUND(HttpStatus.NOT_FOUND.value(),"KHÔNG TÌM THẤY NGƯỜI DÙNG", HttpStatus.NOT_FOUND),
    POST_NOTFOUND(HttpStatus.NOT_FOUND.value(),"KHÔNG TÌM THẤY BÀI VIẾT", HttpStatus.NOT_FOUND),
    TAG_EXISTED(HttpStatus.CONFLICT.value(), "ĐÃ TỒN TẠI TAG",HttpStatus.CONFLICT),
    TAG_NOTFOUND(HttpStatus.NOT_FOUND.value(),"KHÔNG TÌM THẤY TAG", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(HttpStatus.CONFLICT.value(), "EMAIL ĐÃ TỒN TẠI",HttpStatus.CONFLICT)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
