package com.rasello.auth.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetTokenDto {
    private String token;
    private String email;
    private String userId;

    public ResetTokenDto(String token, String email, String userId) {
        this.token = token;
        this.email = email;
        this.userId = userId;
    }
}
