package com.rasello.auth.base;

import javax.persistence.EntityManager;

public interface IBaseDto<T> {
    T toEntity(EntityManager em);
}
