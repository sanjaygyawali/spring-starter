package com.rasello.auth.base.annotation;

import com.rasello.auth.base.Mapper;

public @interface DtoMapper {
    Class<?extends Mapper> mapper();
}
