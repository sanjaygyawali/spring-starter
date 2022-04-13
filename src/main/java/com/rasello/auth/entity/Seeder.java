package com.rasello.auth.entity;

import com.rasello.auth.core.services.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "seeders")
public class Seeder  extends BaseEntity {
    private String name;
    private String environment;
}
