package com.rasello.auth.validation.annotation;

import com.rasello.auth.validation.validator.UniqueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueConstraintValidator.class})
public @interface Unique {
    String message() default "Field already exists";
    Class<?> entity();
    String[] entityFields();
    String[] valueFields();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
