package com.rasello.auth.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasello.auth.core.services.entity.BaseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class MetaDataReadWriteService {

    private final static String BASE_PATH = "src/main/resources/seeds/";
    public void write(String entityName, BaseEntity content) throws IOException {
        createSeedFolder(entityName);
        writeSeedToFile(entityName, content);
    }

    public String readSeedFile(Long id, String entityName) throws IOException {
        String filePathString = constructFilePath(id.toString(), entityName);
        Path filePath = Path.of(filePathString);
        String data =  Files.readString(filePath);
        return  data;
    }

    private void createSeedFolder(String entityName){
        String path = BASE_PATH.concat(entityName);
        new File(path).mkdirs();
    }

    private String constructFilePath(String id, String entityName){
        return BASE_PATH.concat(entityName).concat("/").concat(id);
    }

    private void writeSeedToFile(String entityName, BaseEntity object) throws IOException {
        String id = object.getId().toString();
        File file = new File(constructFilePath(id, entityName));
        FileWriter writer = new FileWriter(file);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(object);
        writer.write(jsonString);
        writer.close();
    }

    public String[] getAllSeedDocuments(String entityName){
        String directoryPath = BASE_PATH.concat(entityName);
        File file = new File(directoryPath);
        String[] fileList = file.list();
        return fileList;
    }

}
