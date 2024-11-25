package com.example.blogapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern,String> {
    private Enum<?>[] enumPatterns;
    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        enumPatterns=constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value==null)
            return true;
        for (Enum<?> anEnum:enumPatterns){
            if (anEnum.name().equals(value))
                return true;
        }
        return false;
    }

}
