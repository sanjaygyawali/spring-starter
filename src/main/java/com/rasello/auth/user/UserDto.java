package com.rasello.auth.user;

import com.rasello.auth.validation.annotation.Unique;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Unique(entity = User.class, fields = "email")
@Validated
public class UserDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
}
