package com.rasello.auth.validation.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {
    String message() default "{javax.validation.constraints.NotBlank.message}";
    Class<?> entity();
    String[] entityFields();
    String[] valueFields();
}
