package com.rasello.auth.core.services.dto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.lang.annotation.Documented;

@Schema(title = "Test Dto")
@Data
public class TestDto {
    private Long id;
    private String testString;
}
