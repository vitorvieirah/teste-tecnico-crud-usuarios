package com.example.user_technical_test.aplication.usecases;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.entrypoint.dto.LoginRespostaDto;
import com.example.user_technical_test.infrastructure.security.TokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserUseCase userUseCase;
    private final EncryptUseCase encryptUseCase;
    private final TokenUseCase tokenUseCase;

    public LoginRespostaDto login(String email, String password) {
        User user = userUseCase.consultarPorEmail(email);
        if(encryptUseCase.validatePassword(password, user.getPassword())) {
            String token = tokenUseCase.generateToken(user);
            return LoginRespostaDto.builder().nome(user.getName()).token(token).build();
        } else  {
            throw new RuntimeException("Usuário digitou senha errada.");
        }
    }
}
