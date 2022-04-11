package com.rasello.auth.repository;

import com.rasello.auth.core.services.entity.Forms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Forms, Long> {
}
