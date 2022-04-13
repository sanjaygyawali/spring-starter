package com.rasello.auth.controller;

import com.rasello.auth.core.common.HeaderUtil;
import com.rasello.auth.core.common.ResponseUtil;
import com.rasello.auth.core.services.entity.Forms;
import com.rasello.auth.repository.FormRepository;
import com.rasello.auth.services.FormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forms")
public class FormController {

    private final FormService formService;
    private final FormRepository formRepository;


    public FormController(FormService formService, FormRepository formRepository) {
        this.formService = formService;
        this.formRepository = formRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Forms> create(@RequestBody Forms form) throws URISyntaxException {
        Forms result = formService.save(form);
        return ResponseEntity
                .created(new URI("/api/countries/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("TEST", true, "forms", result.getId().toString()))
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Forms> update(@PathVariable(value = "id", required = false) final Long id, @RequestBody Forms form)
            throws URISyntaxException {
        Forms result = formService.save(form);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert("TEST", true, "ENTITY", form.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Forms> partialUpdate(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody Forms form
    ) throws URISyntaxException {
        Optional<Forms> result = formService.partialUpdate(form);
        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert("test", true, "forms", form.getId().toString())
        );
    }

    @GetMapping("")
    public List<Forms> getAllForms() {
        var data = formService.findAll();
        return data;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forms> get(@PathVariable Long id) {
        Optional<Forms> form = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(form);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        formService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert("APP NAME", true, "FORMS ", id.toString()))
                .build();
    }


}
