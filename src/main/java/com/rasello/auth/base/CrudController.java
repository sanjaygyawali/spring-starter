package com.rasello.auth.base;

import com.rasello.auth.base.annotation.Delete;
import com.rasello.auth.base.annotation.Get;
import com.rasello.auth.base.annotation.Post;
import com.rasello.auth.base.annotation.Put;
import com.rasello.auth.response.ApiListResponse;
import com.rasello.auth.response.ApiResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public abstract class CrudController<T, V, D> {
    /*protected Class<T> getEntityType(){
        return
    }*/
    protected abstract Class<?> getEntityClass();

    protected abstract Class<?> getIdClass();

    protected abstract Class<?> getRequestDtoClass();

    @Get
    protected ApiListResponse<T> getAll() {
        return new ApiListResponse<>();
    }

    @Get("{id}")
    protected ApiResponse<T> get(@PathVariable("id") V id) {
        return new ApiResponse<>();
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
