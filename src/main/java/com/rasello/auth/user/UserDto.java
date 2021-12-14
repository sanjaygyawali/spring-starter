package com.rasello.auth.user;

import com.rasello.auth.validation.annotation.Unique;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Unique(entity = User.class, fields = {"email"}, message = "Email Already registered")
public class UserDto {
    private String firstName;

    @NotBlank
    private String middleName;
    private String lastName;
    private String email;
    private String password;
}
