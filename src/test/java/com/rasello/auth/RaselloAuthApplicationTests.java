package com.rasello.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RaselloAuthApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private RaselloAuthApplication authApplication;

    @Test
    public void contextLoads() {
        assertThat(passwordEncoder).isNotNull();
    }

}
