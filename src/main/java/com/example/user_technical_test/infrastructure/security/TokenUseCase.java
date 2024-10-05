package com.example.user_technical_test.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.user_technical_test.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenUseCase {

    @Value("${api.security.token.secret}")
    private String chaveSecreta;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(chaveSecreta);
            String token = JWT.create()
                    .withIssuer("api-autenticacao-login")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.geraDataDeExpiracao())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao autenticartoken");
        }
    }

    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(chaveSecreta);
            return  JWT.require(algorithm)
                    .withIssuer("api-autenticacao-login")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant geraDataDeExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
