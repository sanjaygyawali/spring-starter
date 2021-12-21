package com.rasello.auth.base;

import com.rasello.auth.user.User;

import java.util.List;
import java.util.UUID;

public interface CrudService {

    <T> List<T> getAll(Class<T> clazz);

    <T, V> T get(V id, Class<T> clazz);
}
