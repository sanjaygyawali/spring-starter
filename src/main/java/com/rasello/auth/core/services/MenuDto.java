package com.rasello.auth.core.services;

import com.rasello.auth.base.BaseDto;
import com.rasello.auth.core.services.entity.Menus;

import javax.persistence.EntityManager;

public class MenuDto  implements BaseDto<Menus> {
    private String label;
    private String link;
    private Integer menuOrder;
    private Long menu;
    private String role;

    @Override
    public Menus toEntity(EntityManager em) {

        return null;
    }
}
