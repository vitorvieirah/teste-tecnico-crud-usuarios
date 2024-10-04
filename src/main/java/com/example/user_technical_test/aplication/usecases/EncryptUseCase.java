package com.example.user_technical_test.aplication.usecases;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EncryptUseCase {

    private final PasswordEncoder passwordEncoder;


    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean validatePassword(String password, String encryptPassword) {
        return passwordEncoder.matches(password, encryptPassword);
    }
}
