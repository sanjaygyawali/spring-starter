package com.rasello.auth.response;

import java.util.List;
import java.util.Map;

public class ApiListResponse<T> extends ApiResponse<List<T>>{
    public ApiListResponse() {
    }

    public ApiListResponse(Integer code, String message) {
        super(code, message);
    }

    public ApiListResponse(Integer code, String message, List<T> data) {
        super(code, message, data);
    }

    public ApiListResponse(Integer code, String message, List<T> data, Map<String, List<String>> errors) {
        super(code, message, data, errors);
    }
}
