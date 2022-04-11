package com.rasello.auth.base;

import javax.persistence.EntityManager;

public interface BaseDto<T> {
    T toEntity(EntityManager em);
}
