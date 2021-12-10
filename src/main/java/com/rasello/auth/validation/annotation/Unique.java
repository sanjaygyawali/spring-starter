package com.rasello.auth.validation.annotation;

import com.rasello.auth.validation.validator.UniqueConstraintValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueConstraintValidator.class)
public @interface Unique {
    String message() default "{javax.validation.constraints.NotBlank.message}";
    Class<?> entity();
    String[] fields();
}
