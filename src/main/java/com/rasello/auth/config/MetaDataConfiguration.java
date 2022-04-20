package com.rasello.auth.config;

import com.rasello.auth.base.annotation.EntityController;
import com.rasello.auth.core.annotation.IsMeta;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.entity.Forms;
import com.rasello.auth.entity.Menus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MetaDataConfiguration {

    private EntityManager entityManager;
    private WebApplicationContext appContext;
    private List<Class<? extends BaseEntity>> metaEntities = new ArrayList<>();

    public MetaDataConfiguration(EntityManager entityManager, WebApplicationContext appContext) {
        this.entityManager = entityManager;
        this.appContext = appContext;
    }

    @PostConstruct
    public void onInit(){
        var entities = entityManager.getMetamodel().getEntities();
        for(var entity: entities){
            var clazz = entity.getJavaType();
            var entityListenerAnnotataion  = clazz.getAnnotation(EntityListeners.class);
            var isMetaAnnotation = clazz.getAnnotation(IsMeta.class);
            if(entityListenerAnnotataion != null && isMetaAnnotation != null){
                metaEntities.add((Class<? extends BaseEntity>) clazz);
            }
        }
    }

    public  List<Class<? extends BaseEntity>> getMetaEntities(){
        return this.metaEntities;
    }

}
