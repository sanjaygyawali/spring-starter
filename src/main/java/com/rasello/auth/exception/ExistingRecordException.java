package com.rasello.auth.exception;

public class ExistingRecordException extends RuntimeException {
    public ExistingRecordException(String message) {
        super(message);
    }
}
