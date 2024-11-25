package com.example.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả các URL
                .allowedOrigins("http://localhost:4200") // Cho phép từ Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // Thêm OPTIONS cho preflight request
                .allowedHeaders("*") // Cho phép tất cả header
                .allowCredentials(true) // Hỗ trợ cookie/session
                .maxAge(3600); // Cache 1 giờ
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:4200");  // Cấp quyền cho frontend Angular
//        configuration.addAllowedMethod("*");  // Cấp quyền cho tất cả các phương thức
//        configuration.addAllowedHeader("*");  // Cấp quyền cho tất cả các header
//        configuration.setAllowCredentials(true);  // Hỗ trợ cookie/session
//        return request -> configuration;
//    }
}
