package com.rasello.auth.services;

import com.rasello.auth.entity.Menus;

import java.util.List;

public interface MenuService extends IBaseService<Menus> {
    List<Menus> getAllMenusForUser();
}
