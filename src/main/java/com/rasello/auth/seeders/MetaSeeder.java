package com.rasello.auth.seeders;

import com.rasello.auth.core.enums.Environment;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.entity.Seeder;
import com.rasello.auth.repository.SeederRepository;

import java.util.List;

public class MetaSeeder {
    private SeederRepository repository;

    public MetaSeeder(SeederRepository repository) {
        this.repository = repository;
    }

    List<BaseEntity> seederRow(Environment environment, String entityName){
        return  this.repository.findByEnvironmentAndName(environment.name(), entityName);
    }

    public Boolean isSeedNeeded(Environment environment, String entityName){
        return this.seederRow(environment, entityName).size() <= 0 ;
    }

    public void updateSeedEnvironment(Environment environment, String entityName){
        Seeder seed = new Seeder();
        seed.setEnvironment(environment.name());
        seed.setName(entityName);
        repository.save(seed);
    }
}
