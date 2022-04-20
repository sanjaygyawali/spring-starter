package com.rasello.auth.core.services;

import com.rasello.auth.core.services.entity.BaseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.IOException;

@Component
public class MetaListener {

    private MetaDataReadWriteService metaDataReadWriteService;

    public MetaListener(MetaDataReadWriteService seedReadWriteService) {
        this.metaDataReadWriteService = seedReadWriteService;
    }

    @PrePersist
    @PreUpdate
    private void  handleBeforeAnyUpdate(BaseEntity baseEntity){
        var test = 1;
    }

    @PostPersist
    @PostUpdate
    private void handleAfterSaveOrUpdate(BaseEntity entity) throws IOException {
        metaDataReadWriteService.write(entity.getClass().getSimpleName(), entity);
    }
}
