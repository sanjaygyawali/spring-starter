package com.rasello.auth.core.services.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "menus")
public class Menus extends BaseEntity {
    private String label;
    private String link;
    private Integer menuOrder;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menus menu;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Menus parentMenu;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private Set<Menus> menus;

    private String role;
}
