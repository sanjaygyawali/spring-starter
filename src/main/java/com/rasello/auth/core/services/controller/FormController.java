package com.rasello.auth.core.services.controller;

import com.rasello.auth.base.BaseDto;
import com.rasello.auth.core.services.DatabaseServices;
import com.rasello.auth.core.services.MenuDto;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.core.services.entity.Menus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController
//@RequestMapping("resource/{form}")
//@RequiredArgsConstructor
public class FormController {

//    TODO: for all response manage proper response format also paginated list is implemented by this method.
    public void list(@PathVariable("form") String form){
        //TODO: get data for update.
    }

    @GetMapping("/")
    public List get(@PathVariable("form") String form){
        var formModelService = DatabaseServices.getModelService(form);
        return formModelService.findAll();
    }

    @GetMapping("/{id}")
    public BaseEntity getSingleData(@PathVariable("form") String form , @PathVariable("id") Long id) {
        var formModelService = DatabaseServices.getModelService(form);
        return formModelService.findById(id);
    }

    @PostMapping("/")
    public MenuDto save(@PathVariable("form") String form, @RequestBody BaseDto item){
        var formModelService = DatabaseServices.getModelService(form);
        formModelService.save(item);
        return null;
    }

    @PutMapping("/{id}")
    public BaseEntity updateSingleEntity(@PathVariable("form") String form,
                                         @PathVariable("id") Long id,
                                         @RequestBody Menus item ){
        var formModelService = DatabaseServices.getModelService(form);
        return formModelService.updateOneById(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable("form") String form, @PathVariable("id") Long id){
        var formModelService = DatabaseServices.getModelService(form);
        formModelService.deleteById(id);
    }

    public void bulk(@PathVariable("form") String form){
        //TODO: update or create.
    }
}

