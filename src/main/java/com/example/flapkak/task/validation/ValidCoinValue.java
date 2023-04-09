package com.example.flapkak.task.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCoinValueValidator.class)
public @interface ValidCoinValue {

    String message() default "Invalid coin value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}