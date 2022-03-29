package com.rasello.auth.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Data
//@NoArgsConstructor
public class JwtData {
    private String accessToken;
    private String refreshToken;
    private Long expiresAt;

    public JwtData(String accessToken, String refreshToken, Long expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }
}
