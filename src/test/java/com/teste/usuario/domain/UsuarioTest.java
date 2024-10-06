package com.teste.usuario.domain;

import com.teste.usuario.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UsuarioTest {

    private final Usuario usuarioTest = Usuario.builder()
            .id(1L)
            .nome("User Test")
            .email("emailteste@gmail.com")
            .senha("senhateste123")
            .dataCadastro(LocalDateTime.now())
            .build();

    @Test
    void testeMetodoALteracaoDeDados() {
        Usuario novosDados = Usuario.builder().nome("Novo Nome Teste").email("novoemailteste@gmail.com").senha("novasenhacriptografada123").build();
        usuarioTest.setDados(novosDados);
        UserValidator.userValidate(novosDados, usuarioTest);
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasNome() {
        Usuario novosDados = Usuario.builder().nome("Novo Nome Teste").build();
        usuarioTest.setDados(novosDados);
        Assertions.assertEquals(novosDados.getNome(), usuarioTest.getNome());
        Assertions.assertNotEquals(novosDados.getEmail(), usuarioTest.getEmail());
        Assertions.assertNotEquals(novosDados.getSenha(), usuarioTest.getSenha());
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasEmail() {
        Usuario novosDados = Usuario.builder().email("novoemailteste@gmail.com").build();
        usuarioTest.setDados(novosDados);
        Assertions.assertEquals(novosDados.getEmail(), usuarioTest.getEmail());
        Assertions.assertNotEquals(novosDados.getNome(), usuarioTest.getNome());
        Assertions.assertNotEquals(novosDados.getSenha(), usuarioTest.getSenha());
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasSenha() {
        Usuario novosDados = Usuario.builder().senha("novasenhacriptografada123").build();
        usuarioTest.setDados(novosDados);
        Assertions.assertNotEquals(novosDados.getNome(), usuarioTest.getNome());
        Assertions.assertNotEquals(novosDados.getEmail(), usuarioTest.getEmail());
        Assertions.assertEquals(novosDados.getSenha(), usuarioTest.getSenha());
    }
}
