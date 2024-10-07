package com.teste.usuario.aplication.usecases;

import com.teste.usuario.aplication.gateways.TokenGateway;
import com.teste.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoUseCase {

    private final TokenGateway gateway;

    public String gerarToken(Usuario usuario) {
        return gateway.geradorToken(usuario.getEmail());
    }

    public String validarToken(String token) {
        return gateway.validaToken(token);
    }
}
