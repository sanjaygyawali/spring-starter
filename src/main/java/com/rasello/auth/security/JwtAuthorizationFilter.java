package com.rasello.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenHandler jwtTokenHandler;

    public JwtAuthorizationFilter(AuthenticationManager authManager, JwtTokenHandler jwtTokenHandler) {
        super(authManager);
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        var user = jwtTokenHandler.validateAccessToken(header.replace("Bearer ", ""));
        if (user == null) {
            chain.doFilter(req, res);
            return;
        }
        var permissions = user.getRole().getPermissions();
        var authentication = new UsernamePasswordAuthenticationToken(user, null, permissions);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
}
