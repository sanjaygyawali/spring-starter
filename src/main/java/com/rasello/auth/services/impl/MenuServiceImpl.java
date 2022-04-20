package com.rasello.auth.services.impl;

import com.rasello.auth.entity.Menus;
import com.rasello.auth.repository.MenuRepository;
import com.rasello.auth.services.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }


    @Override
//    @Seedable
    public Menus save(Menus entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Menus> partialUpdate(Menus entity) {
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
    public List<Menus> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Menus> findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


    @Override
    public List<Menus> getAllMenusForUser() {
       return this.repository.findAll();
    }
}
