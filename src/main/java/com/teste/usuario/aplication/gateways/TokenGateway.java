package com.teste.usuario.aplication.gateways;

public interface TokenGateway {
    String geradorToken(String email);

    String validaToken(String token);
}
