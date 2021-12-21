package com.rasello.auth.base;

import com.rasello.auth.base.annotation.Delete;
import com.rasello.auth.base.annotation.Get;
import com.rasello.auth.base.annotation.Post;
import com.rasello.auth.base.annotation.Put;
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

    protected abstract Class<T> getEntityClass();

    protected abstract Class<?> getIdClass();

    protected abstract Class<?> getRequestDtoClass();

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
        return new ApiResponse<>();
    }

    @Delete("{id}")
    protected ApiResponse<T> delete(@PathVariable("id") V id) {
        return new ApiResponse<>();
    }


}
