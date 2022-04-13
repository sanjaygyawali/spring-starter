package com.rasello.auth.controller;

import com.rasello.auth.core.common.HeaderUtil;
import com.rasello.auth.core.common.ResponseUtil;
import com.rasello.auth.core.services.entity.Menus;
import com.rasello.auth.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService entityService;

    public MenuController(MenuService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("/")
    public ResponseEntity<Menus> create(@RequestBody Menus entity) throws URISyntaxException {
        Menus result = entityService.save(entity);
        return ResponseEntity
                .created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("TEST", true, "forms", result.getId().toString()))
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Menus> update(@PathVariable(value = "id", required = false) final Long id, @RequestBody Menus entity)
            throws URISyntaxException {
        Menus result = entityService.save(entity);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert("TEST", true, "ENTITY", entity.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Menus> partialUpdate(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody Menus entity
    ) throws URISyntaxException {
        Optional<Menus> result = entityService.partialUpdate(entity);
        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert("test", true, "forms", entity.getId().toString())
        );
    }

    @GetMapping("")
    public List<Menus> getAll() {
        var data = entityService.findAll();
        return data;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menus> get(@PathVariable Long id) {
        Optional<Menus> form = entityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(form);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        entityService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert("APP NAME", true, "FORMS ", id.toString()))
                .build();
    }
}
