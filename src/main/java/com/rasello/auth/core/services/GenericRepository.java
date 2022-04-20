package com.rasello.auth.core.services;

import com.rasello.auth.entity.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class GenericRepository {



    private WebApplicationContext appContext;

    public GenericRepository(WebApplicationContext appContext) {
        this.appContext = appContext;
    }

    public JpaRepository getRepository(Class<?> entity) {
        Repositories repositories = new Repositories(appContext);
        var repo = (JpaRepository) repositories.getRepositoryFor(entity).orElseThrow(() -> {
            return new RuntimeException("No repository found for entity class " + entity.getName());
        });
        return repo;
    }

    public Object save(Class<?> entity) {
        return getRepository(entity).save(entity);
    }

    public Object findAll(Class<?>  entity) {
        return getRepository(entity).findAll();
    }

    public void delete(Class<?>  entity) {
        getRepository(entity).delete(entity);
    }
}