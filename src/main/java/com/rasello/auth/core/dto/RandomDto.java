package com.rasello.auth.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema()
@Data()
public class RandomDto {
    private Long testId;
    private String testIds;
}
