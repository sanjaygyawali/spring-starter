package com.rasello.auth;

import com.rasello.auth.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import java.lang.annotation.Target;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<?>> handleValidationErrors(ConstraintViolationException e, WebRequest request) {
        var errors = e.getConstraintViolations().stream().map(v -> {
            var annotation = v.getConstraintDescriptor().getAnnotation();
            var targetClass = annotation.getClass().getAnnotation(Target.class);
            return "";
        }).collect(toList());
        return ResponseEntity.ok(new ApiResponse<>(500, e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errors = ex.getAllErrors();
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
