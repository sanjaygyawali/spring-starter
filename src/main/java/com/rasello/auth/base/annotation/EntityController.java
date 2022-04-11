package com.rasello.auth.base.annotation;

import com.rasello.auth.base.BaseDto;
import com.rasello.auth.core.services.entity.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityController {
    Class<?> entity();
    Class<?> dto();
    Class<?> id() default UUID.class;
}
