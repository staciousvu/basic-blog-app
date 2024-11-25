package com.example.blogapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslatorUtil {
    private static MessageSource messageSource;

    @Autowired
    public TranslatorUtil(MessageSource messageSource) {
        TranslatorUtil.messageSource=messageSource;
    }

    public static String translate(String code){
        Locale locale= LocaleContextHolder.getLocale();
        return messageSource.getMessage(code,null,locale);
    }
}
