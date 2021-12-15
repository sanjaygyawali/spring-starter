package com.rasello.auth.user;

import com.rasello.auth.validation.annotation.Exists;
import com.rasello.auth.validation.annotation.Unique;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Exists(entity = User.class, valueFields = {"email"}, entityFields = "email", message = "Email does not exist")
public class UserDto {
    private String firstName;

    @NotBlank
    private String middleName;
    private String lastName;
    private String email;
    private String password;
}
