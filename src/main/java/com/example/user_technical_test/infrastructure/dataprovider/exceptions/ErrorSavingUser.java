package com.example.user_technical_test.infrastructure.dataprovider.exceptions;

public class ErrorSavingUser extends RuntimeException {
    public ErrorSavingUser(String message) {
        super(message);
    }
}
