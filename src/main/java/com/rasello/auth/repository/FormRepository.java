package com.rasello.auth.repository;

import com.rasello.auth.entity.Forms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Forms, Long>, JpaSpecificationExecutor<Forms> {
    Forms findOneByName(String schemaName);
}
