package com.rasello.auth.base.annotation;

import com.rasello.auth.base.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DtoMapper {
    Class<?extends Mapper<?,?>> mapper();
}
