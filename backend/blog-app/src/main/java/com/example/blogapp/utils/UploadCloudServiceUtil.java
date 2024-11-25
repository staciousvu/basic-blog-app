package com.example.blogapp.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadCloudServiceUtil {
    private final Cloudinary cloudinary;
    public boolean isImage(MultipartFile file){
        String contentType=file.getContentType();
        return contentType!=null && (
                contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/gif")
                        || contentType.equals("image/bmp")
                        || contentType.equals("image/webp"));
    }
    public String uploadCloudinary(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null or empty");
        }
        if (!isImage(file)) {
            throw new IllegalArgumentException("File is not an image");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Filename is null or empty");
        }
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        safeFileName = safeFileName.substring(0, safeFileName.lastIndexOf("."));
        safeFileName = UUID.randomUUID() + "_" + safeFileName;

        try {

            Map<?, ?> upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", safeFileName
            ));
            return Optional.ofNullable(upload.get("secure_url")).map(Object::toString)
                    .orElseThrow(() -> new IOException("Failed to retrieve secure url"));
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
//public Map<String, Object> uploadCloudinary(MultipartFile file) throws IOException {
//    if (file == null || file.isEmpty()) {
//        throw new IllegalArgumentException("File is null or empty");
//    }
//    if (!isImage(file)) {
//        throw new IllegalArgumentException("File is not an image");
//    }
//    String fileName = file.getOriginalFilename();
//    if (fileName == null || fileName.isEmpty()) {
//        throw new IllegalArgumentException("Filename is null or empty");
//    }
//    String safeFileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
//    safeFileName = safeFileName.substring(0, safeFileName.lastIndexOf("."));
//    safeFileName = UUID.randomUUID() + "_" + safeFileName;
//
//    try {
//
//        Map<String, Object> upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
//                "public_id", safeFileName
//        ));
//        return Optional.ofNullable(upload.get("secure_url")).map(Object::toString)
//                .orElseThrow(() -> new IOException("Failed to retrieve secure url"));
//    } catch (IOException e) {
//        throw new IOException(e.getMessage());
//    }
//}


    public void deleteCloudinary(String secure_url) throws IOException {
        if (secure_url==null || secure_url.isEmpty()){
            throw new IllegalArgumentException("Secure url is null or empty");
        }
        try {
            cloudinary.uploader().destroy(extractPublicId(secure_url),ObjectUtils.emptyMap());

        } catch (IOException e) {
            throw new IOException("Error delete image from cloudinary");
        }
    }
    public String extractPublicId(String secure_url){
        if (secure_url==null || secure_url.isEmpty()){
            throw new IllegalArgumentException("Secure url is null or empty");
        }
        return secure_url.substring(secure_url.lastIndexOf("/")+1,secure_url.lastIndexOf("."));
    }
}
