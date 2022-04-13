package com.rasello.auth.core.services;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SeedReadWriteService {

    public void write(String entityName, Object content){
        createSeedFolder(entityName);
    }

    private void createSeedFolder(String entityName){
        String path = "src/main/resources/seeds/".concat(entityName);
        new File(path).mkdirs();
    }
}
