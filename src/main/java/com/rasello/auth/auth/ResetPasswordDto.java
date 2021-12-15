package com.rasello.auth.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ResetPasswordDto {
    @NotNull(message = "User id is required")
    private UUID userId;
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
}
