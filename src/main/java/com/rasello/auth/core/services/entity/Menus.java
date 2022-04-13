package com.rasello.auth.core.services.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "menus")
public class Menus extends BaseEntity {
    private String name;
    private String url;
    private String icon;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Menus schema;

    private String role;
    private Integer menuOrder;
}
