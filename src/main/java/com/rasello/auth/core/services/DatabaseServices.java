package com.rasello.auth.core.services;

import com.rasello.auth.core.dto.DbModule;
import com.rasello.auth.core.services.entity.Forms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class DatabaseServices {
    public static HashMap<String, DatabaseModelService> services = new HashMap<>();

    @Autowired
    EntityManager entityManager;

    @Autowired
    DatabaseModelService databaseModelService;

    @Autowired
    DatabaseModelConfiguration databaseModelConfiguration;

    @PostConstruct
    private void afterConfigurationInit(){
        this.initializeDatabaseModelService();
    }

    private void initializeDatabaseModelService(){
        var mappings = databaseModelConfiguration.fetchEntityMappings();
//        let services = new Data
        for(Map.Entry<String, DbModule> item: mappings.entrySet()) {
            var instance = databaseModelService;
            instance.setEntity(item.getValue().getEntity());
            instance.setDto(item.getValue().getDto());
            instance.setRepository(item.getValue().getRepository());
            try {
                DatabaseServices.services.put(item.getKey(), instance);
            }catch ( Exception e ){
                e.printStackTrace();
            }
        }
    }


    private void getModelName(){
//        TODO: get available entity names.
//
    }

    public static DatabaseModelService  getModelService(String entityName){
//        TODO: if service is not found then throw exception.
        var model = DatabaseServices.services.get(entityName);
        return model;
    }

    @Transactional
    public Forms saveFormTest(Forms form){
//        var formData = new Forms();
//        formData.setName(form.getName());
        entityManager.persist(form);
        return form;
    }
}
