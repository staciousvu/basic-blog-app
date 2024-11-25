package com.example.blogapp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Component
public class FileUploadServiceUtil {
    @Value("${file.upload-dir}")
    private String basePath;
    public boolean isImage(MultipartFile file){
        String contentType=file.getContentType();
        return contentType!=null && (
                contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif")
                || contentType.equals("image/bmp")
                || contentType.equals("image/webp"));
    }
    public String storeFile(MultipartFile file,String subPath) throws IOException {
        if (!isImage(file))
            throw new IOException("Image file not valid or empty");
        String fileName=UUID.randomUUID()+"-"+file.getOriginalFilename();
        String uploadDir=basePath+"/"+subPath+"/";
        Path path=Paths.get(uploadDir+fileName);
        if (!Files.exists(Paths.get(uploadDir))){
            Files.createDirectories(Paths.get(uploadDir));
        }
        Files.write(path,file.getBytes());
        return fileName;
    }
    public void deleteFile(String fileName,String subPath) throws IOException {
        String filePath = basePath + "/" + subPath + "/" + fileName;
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        } else {
            throw new IOException("File not found: " + fileName);
        }
    }
}
