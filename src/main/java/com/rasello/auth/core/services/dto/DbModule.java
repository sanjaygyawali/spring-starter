package com.rasello.auth.core.services.dto;

import com.rasello.auth.base.BaseDto;
import com.rasello.auth.core.services.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

@Builder
@Getter
@Setter
public class DbModule {
    private Class<?extends BaseEntity> entity;
    private Class<?extends BaseDto> dto;


}
