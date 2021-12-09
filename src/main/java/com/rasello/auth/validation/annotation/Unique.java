package com.rasello.auth.validation.annotation;

public @interface Unique {
    String message() default "{javax.validation.constraints.NotBlank.message}";
    Class<?> entity();
    String[] entityFields();
    String[] valueFields();
}
