package com.teste.usuario.domain;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UsuarioTest {

    private final Usuario usuarioTeste = UsuarioBuilder.gerarUsuario();
    private final LocalDateTime dataFixa = UsuarioBuilder.gerarData();

    @Test
    void testeMetodoALteracaoDeDados() {
        Usuario novosDados = Usuario.builder().nome("Novo Nome Teste").email("novoemailteste@gmail.com").senha("novasenhacriptografada123").build();
        usuarioTeste.setDados(novosDados);
        UsuarioValidator.validaUsuario(novosDados, usuarioTeste);
    }

    @Test
    void testeMetodoAleraSenhaDataCadastro() {
        String novaSenha = "novasenhateste123@";
        usuarioTeste.setSenhaDataCadastro(novaSenha, dataFixa);
        Assertions.assertEquals(novaSenha, usuarioTeste.getSenha());
        Assertions.assertEquals(dataFixa, usuarioTeste.getDataCadastro());
    }

}
