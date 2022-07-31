package com.rasello.auth.services.impl;

import com.rasello.auth.base.TableRequest;
import com.rasello.auth.base.TableResponse;
import com.rasello.auth.core.annotation.Seedable;
import com.rasello.auth.core.services.DatatableService;
import com.rasello.auth.entity.Forms;
import com.rasello.auth.repository.FormRepository;
import com.rasello.auth.services.FormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormServiceImpl implements FormService  {

    private final FormRepository repository;
    private final DatatableService datatableService;
    public FormServiceImpl(FormRepository repository, DatatableService datatableService) {
        this.repository = repository;
        this.datatableService = datatableService;
    }

    @Override
    @Seedable
    public Forms save(Forms entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Forms> partialUpdate(Forms entity) {
        return repository
                .findById(entity.getId())
                .map(existingForm -> {
                    if (entity.getName() != null) {
                        existingForm.setName(entity.getName());
                    }
                    return existingForm;
                })
                .map(repository::save);
    }

    @Override
    public List<Forms> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Forms> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }



    public TableResponse<Forms> getDataList(TableRequest tableRequest) {
        return this.datatableService.getDatatable(Forms.class, tableRequest);
    }

    @Override
    public Forms retrieveSchemaForResource(String schemaName) {
        return repository.findOneByName(schemaName);
    }
}
