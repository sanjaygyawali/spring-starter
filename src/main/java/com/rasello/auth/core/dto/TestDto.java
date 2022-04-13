package com.rasello.auth.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "Test Dto")
@Data
public class TestDto {
    private Long id;
    private String testString;
}
