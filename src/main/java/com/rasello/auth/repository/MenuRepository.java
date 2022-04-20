package com.rasello.auth.repository;

import com.rasello.auth.entity.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menus, Long> {
}
