package com.rasello.auth.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EncoderConfigTest {
    private EncoderConfig encoderConfig;

    @BeforeEach
    void setUp() {
        encoderConfig = new EncoderConfig();
    }

    @Test
    void passwordEncoderReturnsNonNullPasswordEncoder() {
        var encoder = encoderConfig.passwordEncoder();
        assertThat(encoder).isNotNull();
    }
}