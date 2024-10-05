package com.example.user_technical_test.infrastructure.dataprovider.exceptions;

public class ErrorConsultUserByEmailException extends RuntimeException {
    public ErrorConsultUserByEmailException(String message) {
        super(message);
    }
}
