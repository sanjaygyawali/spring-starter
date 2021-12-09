package com.rasello.auth.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private Map<String, List<String>> errors;

    public ApiResponse(Integer code, String message) {
        this(code, message, null, null);
    }

    public ApiResponse(Integer code, String message, T data) {
        this(code, message, data, null);
    }

    public ApiResponse(Integer code, String message, T data, Map<String, List<String>> errors) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }
}
