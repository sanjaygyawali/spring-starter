package com.rasello.auth.user;

import com.rasello.auth.base.CrudController;
import com.rasello.auth.base.DatatableController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController extends DatatableController<User, UUID> {

    @Override
    protected Class<?> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<?> getIdClass() {
        return UUID.class;
    }

    @Override
    protected Class<?> getRequestDtoClass(){
        return User.class;
    }
}
