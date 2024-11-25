package com.example.blogapp;

import com.example.blogapp.utils.UploadCloudServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogAppApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext context =SpringApplication.run(BlogAppApplication.class, args);
//        UploadCloudServiceUtil uploadCloudServiceUtil = context.getBean(UploadCloudServiceUtil.class);
//        uploadCloudServiceUtil.deleteCloudinary("https://res.cloudinary.com/dcih8hare/image/upload/v1730688614/c5a0ae17-2f00-4b4d-ac62-742111eb5bb6-vnpay-logo.png.png");
    }

}
