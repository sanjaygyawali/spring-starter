package com.rasello.auth.core.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;

@Schema()
@Data()
public class RandomDto {
    private Long testId;
    private String testIds;
}
