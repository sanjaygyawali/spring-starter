package com.rasello.auth.base;

import com.rasello.auth.base.annotation.Post;
import com.rasello.auth.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class DatatableController<T, I> extends CrudController<T, I> {
    @Autowired
    private DefaultTableService tableService;

    @Post("table")
    protected ApiResponse<?>  generateDataTable(@RequestBody TableRequest request){
        var data = tableService.getDatatable(getEntityClass(), getIdClass(), request);
       return new ApiResponse<>(200, "Success", data);
    }
}
