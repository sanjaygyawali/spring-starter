package com.rasello.auth.validation.annotation;

import com.rasello.auth.validation.validator.ExistsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsConstraintValidator.class)
public @interface Exists {
    String message() default "{javax.validation.constraints.NotBlank.message}";
    Class<?> entity();
    String[] entityFields();
    String[] valueFields();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
