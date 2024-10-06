package com.teste.usuario.aplication.usecases;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CriptografiaUseCase {

    private final PasswordEncoder passwordEncoder;


    public String criptografar(String senha) {
        return passwordEncoder.encode(senha);
    }

    public boolean validarSenha(String senha, String senhaCriptografada) {
        return passwordEncoder.matches(senha, senhaCriptografada);
    }
}
