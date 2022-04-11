package com.rasello.auth.core.services.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "forms")
public class Forms extends BaseEntity {
    private String name;
    private String tag;
    private String structure;
    private String draft;
    private Boolean isEntity;
}
