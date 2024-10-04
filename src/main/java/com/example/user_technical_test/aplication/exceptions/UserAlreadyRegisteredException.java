package com.example.user_technical_test.aplication.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super("Usuário com este email já cadastrado");
    }
}
