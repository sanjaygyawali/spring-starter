package com.rasello.auth.seeders;

import com.rasello.auth.core.interfaces.ISeeder;
import com.rasello.auth.repository.SeederRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuSeeder extends BaseSeeder {

    public MenuSeeder(SeederRepository repository) {
        super(repository);
    }

    @Override
    String getEntityName() {
        return null;
    }

    @Override
    void development() {

    }

    @Override
    void staging() {

    }

    @Override
    void production() {

    }
}
