package com.rasello.auth.services.impl;

import com.rasello.auth.entity.Seeder;
import com.rasello.auth.repository.SeederRepository;
import com.rasello.auth.services.SeederService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeederServiceImpl implements SeederService {

    private SeederRepository repository;

    public SeederServiceImpl(SeederRepository repository) {
        this.repository = repository;
    }

    @Override
    public Seeder save(Seeder entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Seeder> partialUpdate(Seeder entity) {
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
    public List<Seeder> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Seeder> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
