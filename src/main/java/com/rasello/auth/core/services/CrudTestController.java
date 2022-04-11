package com.rasello.auth.core.services;

import com.rasello.auth.base.CrudController;
import com.rasello.auth.base.annotation.EntityController;
import com.rasello.auth.base.annotation.Get;
import com.rasello.auth.core.services.entity.BaseEntity;
import com.rasello.auth.core.services.entity.Forms;
import com.rasello.auth.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("forms")
@RequiredArgsConstructor
@EntityController(entity = Forms.class,dto = Forms.class, id=Long.class )
public class CrudTestController extends CrudController<Forms, Long, Forms> {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    DatabaseServices databaseServices;

    @Autowired
    DatabaseModelService databaseModelService;
    @Autowired
    private ModelMapper modelMapper;

//    private final DatabaseModelService databaseModelService;

//    @PostMapping("/")
//    public boolean create(@RequestBody Map<String,String> item){
////        var test = this.databaseModelService.findAll();
//        return true;
//    }
//
//    @GetMapping("/{id}")
//    public boolean create(@PathVariable("id") Long id){
////        var test = this.databaseModelService.findById(id);
//        return true;
//    }

    @PostMapping("/save")
    public  BaseEntity save(@RequestBody Forms item){
        var formModelService = DatabaseServices.getModelService("forms");
        formModelService.save(item);
        return item;
    }

    @GetMapping("/get")
    public List get(){
        var formModelService = DatabaseServices.getModelService("forms");
        return formModelService.findAll();
    }

    @GetMapping("/gets/{id}")
    public BaseEntity getSingleData(@PathVariable("id") Long id) {
        var formModelService = DatabaseServices.getModelService("forms");
        return formModelService.findById(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteByID(@PathVariable("id") Long id){
        var formModelService = DatabaseServices.getModelService("forms");
        formModelService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public BaseEntity updateSingleEntity(@PathVariable("id") Long id,@RequestBody Forms item ){
        var formModelService = DatabaseServices.getModelService("forms");
        return formModelService.updateOneById(id, item);
    }
}
