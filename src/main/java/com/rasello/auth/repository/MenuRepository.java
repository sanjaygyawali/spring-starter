package com.rasello.auth.repository;

import com.rasello.auth.core.services.entity.Forms;
import com.rasello.auth.core.services.entity.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menus, Long> {
}
