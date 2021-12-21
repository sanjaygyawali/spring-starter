package com.rasello.auth.base;

import com.rasello.auth.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class DefaultCrudService implements CrudService {
    private final EntityManager entityManager;

    public DefaultCrudService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return entityManager.createQuery(String.format("SELECT d from %s d", clazz.getSimpleName()), clazz).getResultList();
    }

    @Override
    public <T, V> T get(V id, Class<T> clazz) {
        var data = entityManager.createQuery(String.format("SELECT d from %s d where d.id = %s", clazz.getSimpleName(), id), clazz).getSingleResult();
        if (data == null)
            throw new RecordNotFoundException("Record not found");
        return data;
    }
}
