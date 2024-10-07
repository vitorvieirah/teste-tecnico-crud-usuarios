package com.teste.usuario.aplication.usecases;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.entrypoint.dto.LoginRespostaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UsuarioUseCase usuarioUseCase;
    private final CriptografiaUseCase criptografiaUseCase;
    private final AutenticacaoUseCase autenticacaoUseCase;

    public LoginRespostaDto login(String email, String senha) {
        Usuario usuario = usuarioUseCase.consultarPorEmail(email);

        if (!criptografiaUseCase.validarSenha(senha, usuario.getSenha())) {
            throw new RuntimeException("Usuário digitou senha errada.");
        }

        String token = autenticacaoUseCase.gerarToken(usuario);
        return LoginRespostaDto.builder().nome(usuario.getNome()).token(token).build();
    }
}
