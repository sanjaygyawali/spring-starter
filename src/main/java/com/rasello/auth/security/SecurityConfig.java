package com.rasello.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenHandler jwtTokenHandler;

    public SecurityConfig(JwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/me").authenticated()
                .antMatchers(
                        "/api/auth/**",
                        "/api/user/**",
                        "/api/register/verify/**"
                ).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/login",
                        "/webjars/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.ttf",
                        "/**/*.woff",
                        "/**/*.woff2",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/**/*.js",
                        "/**/*.js.map")
                .permitAll()
                .antMatchers("/api/**")
                .authenticated()
                .antMatchers("/**")
                .permitAll()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManagerBean(), jwtTokenHandler))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login", "/api/auth/login");

        web.ignoring()
                .antMatchers("/v3/api-docs/**") //
                .antMatchers("/swagger-resources/**") //
                .antMatchers("/swagger-ui/**") //
                .antMatchers("/webjars/**"); //
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
