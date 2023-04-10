package com.example.flapkak.task.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCoinValueValidator implements ConstraintValidator<ValidCoinValue, Integer> {

    @Override
    public void initialize(ValidCoinValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are allowed
        }

        return value == 5 || value == 10 || value == 20 || value == 50 || value == 100;
    }
}