package com.rasello.auth.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasello.auth.config.MetaDataConfiguration;
import com.rasello.auth.core.enums.Environment;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.repository.SeederRepository;
import com.rasello.auth.seeders.MetaSeeder;
import org.hibernate.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Component
@Transactional
public class MetaDataLoader extends MetaSeeder implements CommandLineRunner {
    private MetaDataReadWriteService metaDataReadWriteService;
    private MetaDataConfiguration metaDataConfiguration;
    private GenericRepository genericRepository;
    private EntityManager entityManager;

    public MetaDataLoader(MetaDataReadWriteService seedReadWriteService,
                          GenericRepository genericRepository,
                          MetaDataConfiguration metaDataConfiguration,
                          SeederRepository seederRepository,
                          EntityManager entityManager) {
        super(seederRepository);
        this.metaDataReadWriteService = seedReadWriteService;
        this.genericRepository = genericRepository;
        this.metaDataConfiguration = metaDataConfiguration;
        this.entityManager = entityManager;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMetaData();
    }

    public void loadMetaData() throws IOException {
//        TODO: get current environment;
        Environment env = Environment.Development;
        var seedOrders = metaDataConfiguration.getMetaEntities();
        for (Class<? extends BaseEntity> entity : seedOrders) {
            String[] documents = metaDataReadWriteService.getAllSeedDocuments(entity.getSimpleName());
            if (documents != null) {
                if (this.isSeedNeeded(Environment.Development, entity.getSimpleName())) {
                    for (String document : documents) {
                        Long id = Long.parseLong(document);

                        String jsonDoc = metaDataReadWriteService.readSeedFile(id, entity.getSimpleName());
                        var repo = genericRepository.getRepository(entity);
                        ObjectMapper objectMapper = new ObjectMapper();
                        var dbDataValue = repo.findById(id);
                        BaseEntity fileDataValue;
                        fileDataValue = objectMapper.readValue(jsonDoc, entity);
                        fileDataValue.setId(id);
                        repo.save(fileDataValue);
                    }
                    this.updateSeedEnvironment(env, entity.getSimpleName());
                }
            }
        }
    }
}
