package com.example.user_technical_test.domain;

import com.example.user_technical_test.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserTest {

    private final User userTest = User.builder()
            .id(1L)
            .name("User Test")
            .email("emailteste@gmail.com")
            .password("senhateste123")
            .registerDate(LocalDateTime.now())
            .build();

    @Test
    void testeMetodoALteracaoDeDados() {
        User novosDados = User.builder().name("Novo Nome Teste").email("novoemailteste@gmail.com").password("novasenhacriptografada123").build();
        userTest.setData(novosDados);
        UserValidator.userValidate(novosDados, userTest);
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasNome() {
        User novosDados = User.builder().name("Novo Nome Teste").build();
        userTest.setData(novosDados);
        Assertions.assertEquals(novosDados.getName(), userTest.getName());
        Assertions.assertNotEquals(novosDados.getEmail(), userTest.getEmail());
        Assertions.assertNotEquals(novosDados.getPassword(), userTest.getPassword());
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasEmail() {
        User novosDados = User.builder().email("novoemailteste@gmail.com").build();
        userTest.setData(novosDados);
        Assertions.assertEquals(novosDados.getEmail(), userTest.getEmail());
        Assertions.assertNotEquals(novosDados.getName(), userTest.getName());
        Assertions.assertNotEquals(novosDados.getPassword(), userTest.getPassword());
    }

    @Test
    void testeMetodoALteracaoDeDadosApenasSenha() {
        User novosDados = User.builder().password("novasenhacriptografada123").build();
        userTest.setData(novosDados);
        Assertions.assertNotEquals(novosDados.getName(), userTest.getName());
        Assertions.assertNotEquals(novosDados.getEmail(), userTest.getEmail());
        Assertions.assertEquals(novosDados.getPassword(), userTest.getPassword());
    }
}
