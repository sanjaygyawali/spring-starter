package com.rasello.auth.core.annotation;

import com.rasello.auth.core.services.MetaListener;

import javax.persistence.EntityListeners;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMeta {
    boolean meta() default true;
}
