package com.rasello.auth.security;

import com.rasello.auth.user.User;
import com.rasello.auth.user.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

//@Service
public class JwtTokenHandler {

    @Value("${jwt.access-secret}")
    protected String accessSecret;

    @Value("${jwt.refresh-secret}")
    protected String refreshSecret;

    @Value("${jwt.issuer}")
    protected String issuer;

    @Value("${jwt.access-validity}")
    protected Long accessValidity;

    @Value("${jwt.refresh-validity}")
    protected Long refreshValidity;

    private final UserServiceImpl userService;

    @Autowired
    public JwtTokenHandler(UserServiceImpl userService) {
        this.userService = userService;
    }

    public JwtData generateJwtData(User user) {
        var issuedAt = new Date();
        var accessToken = this.generateAccessToken(user, issuedAt);
        var refreshToken = this.generateRefreshToken(user, issuedAt);
        return new JwtData(accessToken, refreshToken, issuedAt.getTime() + accessValidity);
    }

    public String generateAccessToken(User user, Date issuedAt) {
        //TODO add user privileges
        var accessExpiryDate = new Date(issuedAt.getTime() + accessValidity);
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(accessExpiryDate)
                .signWith(Keys.hmacShaKeyFor(accessSecret.getBytes()))
                .setId(UUID.randomUUID().toString())
                .compact();
    }

    public String generateRefreshToken(User user, Date issuedAt) {
        var refreshExpiryDate = new Date(issuedAt.getTime() + refreshValidity);
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(refreshExpiryDate)
                .signWith(Keys.hmacShaKeyFor(refreshSecret.getBytes()))
                .setId(UUID.randomUUID().toString())
                .compact();
    }

    public User validateAccessToken(String token) {
        var parser = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(Keys.hmacShaKeyFor(accessSecret.getBytes()))
                .build();
        try {
            var userId = parser.parseClaimsJws(token).getBody().getSubject();
            return userService.getUser(UUID.fromString(userId));
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User validateRefreshToken(String token) {
        var parser = Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(Keys.hmacShaKeyFor(refreshSecret.getBytes()))
                .build();
        try {
            var userId = parser.parseClaimsJws(token).getBody().getSubject();
            return userService.getUser(UUID.fromString(userId));
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
    }
}
