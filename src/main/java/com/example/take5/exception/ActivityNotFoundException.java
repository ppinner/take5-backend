package com.example.take5.exception;

public class ActivityNotFoundException extends RuntimeException {

    public ActivityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}