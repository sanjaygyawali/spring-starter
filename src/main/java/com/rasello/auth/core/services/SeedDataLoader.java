package com.rasello.auth.core.services;

import com.rasello.auth.seeders.BaseSeeder;
import com.rasello.auth.seeders.MenuSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeedDataLoader implements CommandLineRunner {
    private MenuSeeder menuSeeder;

    public SeedDataLoader(MenuSeeder menuSeeder) {
        this.menuSeeder = menuSeeder;
    }

    private  void runDependencies(){
        this.menuSeeder.run();
    }

    @Override
    public void run(String... args) throws Exception {
        this.runDependencies();
    }
}
