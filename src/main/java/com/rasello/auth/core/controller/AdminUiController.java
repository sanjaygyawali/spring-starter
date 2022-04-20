package com.rasello.auth.core.controller;

import com.rasello.auth.entity.Forms;
import com.rasello.auth.entity.Menus;
import com.rasello.auth.services.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("a/ui")
public class AdminUiController {

    private MenuService menuService;

    public AdminUiController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/config")
    public List<Menus> retrieveMenuConfiguration(){
        return this.menuService.getAllMenusForUser();
    }
}
