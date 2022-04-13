package com.rasello.auth.repository;

import com.rasello.auth.core.services.entity.Menus;
import com.rasello.auth.entity.Seeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeederRepository extends JpaRepository<Seeder, Long> {
}
