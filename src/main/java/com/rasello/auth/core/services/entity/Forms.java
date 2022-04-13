package com.rasello.auth.core.services.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;



@Entity
@Data
@NoArgsConstructor
@Table(name = "forms")
public class Forms extends BaseEntity {
    private String name;
    private String tag;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private HashMap<String,Object> structure = new HashMap<>();

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private HashMap<String, Object> draft  = new HashMap<>();

    private Boolean isEntity;
}
