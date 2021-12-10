package com.rasello.auth.user;

import com.rasello.auth.base.DatatableController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController extends DatatableController<User, UUID, UserDto> {

    @Override
    protected Class<?> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<?> getIdClass() {
        return UUID.class;
    }

    @Override
    protected Class<?> getRequestDtoClass() {
        return UserDto.class;
    }
}
