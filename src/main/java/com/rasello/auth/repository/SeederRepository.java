package com.rasello.auth.repository;

import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.entity.Seeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeederRepository extends JpaRepository<Seeder, Long> {
    public List<BaseEntity> findByEnvironmentAndName(String name, String environment);
}
