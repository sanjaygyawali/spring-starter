package com.rasello.auth.services.impl;

import com.rasello.auth.core.services.entity.Forms;
import com.rasello.auth.repository.FormRepository;
import com.rasello.auth.services.FormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final FormRepository repository;

    public FormServiceImpl(FormRepository repository) {
        this.repository = repository;
    }

    @Override
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
}
