package com.example.blogapp.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.UUID;
@NoArgsConstructor
@Component
public class SlugUtil {
    public String generateSlug(String name){
        String slug = name.toLowerCase().replaceAll("đ","d");
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        slug = slug.replaceAll("[^a-z0-9\\s]", "");
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("^-+", "").replaceAll("-+$", "");
        slug+='-'+UUID.randomUUID().toString();
        return slug;
    }
}
