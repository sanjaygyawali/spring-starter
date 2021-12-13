package com.rasello.auth.auth;

import com.rasello.auth.response.ApiResponse;
import com.rasello.auth.user.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ApiResponse<?> resetPassword(){
        var user = userService.getAll().get(0);

    }
}
