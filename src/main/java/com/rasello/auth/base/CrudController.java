package com.rasello.auth.base;

import com.rasello.auth.base.annotation.*;
import com.rasello.auth.response.ApiListResponse;
import com.rasello.auth.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public abstract class CrudController<T, V, D> {
    @Autowired
    protected CrudService crudService;

    protected Class<T> getEntityClass() {
        return (Class<T>) this.getClass().getAnnotation(EntityController.class).entity();
    }

    protected Class<V> getIdClass() {
        return (Class<V>) this.getClass().getAnnotation(EntityController.class).id();
    }

    protected Class<D> getRequestDtoClass() {
        return (Class<D>) this.getClass().getAnnotation(EntityController.class).dto();
    }

    @Get
    protected ApiListResponse<T> getAll() {
        var entityClass = this.getEntityClass();
        var data = crudService.getAll(entityClass);
        return new ApiListResponse<T>(200, "Success", data);
    }

    @Get("{id}")
    protected ApiResponse<T> get(@PathVariable("id") V id) {
        var data = crudService.get(id, getEntityClass());
        return new ApiResponse<>(200, "Success", data);
    }

    @Post
    protected ApiResponse<T> create(@Valid @RequestBody D body, BindingResult result) {
        return new ApiResponse<>();
    }

    @Put("{id}")
    protected ApiResponse<T> update(@PathVariable("id") V id, @Valid @RequestBody D body) {
//        crudService.u
        return new ApiResponse<>();
    }

    @Delete("{id}")
    protected ApiResponse<T> delete(@PathVariable("id") V id) {
        return new ApiResponse<>();
    }


}
