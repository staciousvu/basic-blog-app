package com.example.blogapp.controllers;

import com.example.blogapp.responses.ApiResponse;
import com.example.blogapp.responses.UploadResponse;
import com.example.blogapp.services.post.IPostServiceV3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/upload/image")
@RequiredArgsConstructor
public class UploadCloudController {
    private final IPostServiceV3 serviceV3;
    @PostMapping(value = "",consumes ="multipart/form-data" )
    public ApiResponse<UploadResponse> upload(@RequestPart(value = "file") MultipartFile file) throws IOException {
        return ApiResponse.<UploadResponse>builder()
                .message("Upload successful")
                .data(UploadResponse.builder()
                        .secure_url(serviceV3.uploadImageCloudinary(file))
                        .build())
                .build();
    }
    @PostMapping(value = "/test")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {

        return ResponseEntity.ok(Map.of("url", serviceV3.uploadImageCloudinary(file)));
    }
}
