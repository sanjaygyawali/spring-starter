package com.rasello.auth.user;

import com.rasello.auth.base.DatatableController;
import com.rasello.auth.base.annotation.EntityController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

//@RestController
//@RequestMapping("/api/users")
//@Validated
@EntityController(entity = User.class, dto = UserDto.class)
public class UserController extends DatatableController<User, UUID, UserDto> {

}

