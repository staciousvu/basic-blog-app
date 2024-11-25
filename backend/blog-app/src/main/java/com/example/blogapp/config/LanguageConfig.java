package com.example.blogapp.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LanguageConfig extends AcceptHeaderLocaleResolver {
    List<Locale> locales=List.of(new Locale("vi"),new Locale("fr"),
            new Locale("en"));
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLanguage=request.getHeader("Accept-Language");
        if (headerLanguage ==null || headerLanguage.isEmpty())
            return Locale.ENGLISH;
        return Locale.lookup(Locale.LanguageRange.parse(headerLanguage),locales);
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }


}
