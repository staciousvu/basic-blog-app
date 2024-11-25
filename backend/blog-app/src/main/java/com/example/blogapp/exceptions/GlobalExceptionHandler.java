package com.example.blogapp.exceptions;
import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final static Long currentTime=System.currentTimeMillis();
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handlingRuntimeException(RuntimeException exception){
        ErrorResponse errorResponse=ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .path(getRequestPath())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.internalServerError().body(errorResponse);
    }
    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ErrorResponse> handlingIOException(IOException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .error(exception.toString())
                .message(exception.getMessage())
                .path(getRequestPath())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(value = BlogAppException.class)
    public ResponseEntity<ErrorResponse> handlingBlogAppException(BlogAppException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .error(exception.getErrorCode().getStatusCode().toString())
                .message(exception.getMessage())
                .path(getRequestPath())
                .build();

        return ResponseEntity.status(exception.getErrorCode().getStatusCode()).body(errorResponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ErrorResponse.FieldError> fieldErrors = exception.getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .error(HttpStatus.BAD_REQUEST.toString())
                .path(getRequestPath())
                .errors(fieldErrors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    // Thêm xử lý cho các ngoại lệ liên quan đến xác thực người dùng
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handlingAuthenticationException(AuthenticationException exception) {
        log.error("AuthenticationException occurred: {}", exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .error(HttpStatus.UNAUTHORIZED.toString())
                .message("Authentication failed: " + exception.getMessage())
                .path(getRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Thêm xử lý cho ngoại lệ AccessDeniedException (Khi người dùng không có quyền truy cập)
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        log.error("AccessDeniedException occurred: {}", exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(convertMillisToDateTime(currentTime))
                .error(HttpStatus.FORBIDDEN.toString())
                .message("Access denied: " + exception.getMessage())
                .path(getRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    public String convertMillisToDateTime(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    public String getRequestPath(){
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request.getServletPath();
    }
}
