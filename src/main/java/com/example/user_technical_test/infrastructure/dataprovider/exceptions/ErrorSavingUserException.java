package com.example.user_technical_test.infrastructure.dataprovider.exceptions;

public class ErrorSavingUserException extends RuntimeException {
    public ErrorSavingUserException(String message) {
        super(message);
    }
}
