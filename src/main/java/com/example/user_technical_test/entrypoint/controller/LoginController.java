package com.example.user_technical_test.entrypoint.controller;

import com.example.user_technical_test.aplication.usecases.LoginUseCase;
import com.example.user_technical_test.entrypoint.dto.LoginDto;
import com.example.user_technical_test.entrypoint.dto.LoginRespostaDto;
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
    public ResponseEntity<LoginRespostaDto> login(@RequestBody LoginDto body) {
        LoginRespostaDto respostaLogin = useCase.login(body.email(), body.password());
        return ResponseEntity.ok(respostaLogin);
    }
}
