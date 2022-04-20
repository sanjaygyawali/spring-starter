package com.rasello.auth.entity;

import com.rasello.auth.core.annotation.IsMeta;
import com.rasello.auth.core.services.MetaListener;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.HashMap;



@Entity
@Data
@NoArgsConstructor
@Table(name = "forms")
@EntityListeners(MetaListener.class)
@IsMeta
public class
Forms extends BaseEntity {
    private String name;
    private String tag;

//    TODO: change structure column name to schema.
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private HashMap<String,Object> schema = new HashMap<>();

    @Type(type = "json")
    @Column(name = "draft_schema", columnDefinition = "json")
    private HashMap<String, Object> draftSchema  = new HashMap<>();

    private Boolean isEntity;

    @Type(type = "json")
    @Column(name = "display_schema", columnDefinition = "json")
    private HashMap<String, Object> displaySchema = new HashMap<>();
}
