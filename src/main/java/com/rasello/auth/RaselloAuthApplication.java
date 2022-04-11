package com.rasello.auth;

import com.rasello.auth.repository.FormRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

@ComponentScan(basePackages = "com.rasello.auth")
@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class RaselloAuthApplication {

    List<FormRepository> formRepoList;

    @PostConstruct()
    public void onCreate(){
        var test = 1;
    }


    public static void main(String[] args) {
        try {
            SpringApplication.run(RaselloAuthApplication.class, args);
        }catch (Exception error){
            log.error(error.toString());
        }
    }


//    @Bean
//    public KeycloakConfigResolver keycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }

}
