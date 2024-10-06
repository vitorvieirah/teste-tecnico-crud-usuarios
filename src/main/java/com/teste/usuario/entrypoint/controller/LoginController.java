package com.teste.usuario.entrypoint.controller;

import com.teste.usuario.aplication.usecases.LoginUseCase;
import com.teste.usuario.entrypoint.dto.LoginDto;
import com.teste.usuario.entrypoint.dto.LoginRespostaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase useCase;

    @PostMapping
    public ResponseEntity<LoginRespostaDto> login(@Valid @RequestBody LoginDto body) {
        LoginRespostaDto respostaLogin = useCase.login(body.getEmail(), body.getSenha());
        return ResponseEntity.ok(respostaLogin);
    }
}
