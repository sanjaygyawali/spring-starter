package com.rasello.auth.base;

public interface TableService {
    <E, I> TableResponse<E> getDatatable(Class<E> entityClass, Class<I> idClass, TableRequest request);
}
