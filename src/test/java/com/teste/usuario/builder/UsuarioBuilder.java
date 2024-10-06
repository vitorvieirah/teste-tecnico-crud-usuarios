package com.teste.usuario.builder;

import com.teste.usuario.domain.Usuario;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UsuarioBuilder {



    public static Long gerarId() {
        return 1L;
    }

    public static LocalDateTime gerarData() {
        return LocalDateTime.of(2024, 10, 5, 12, 23, 42);
    }

    public static Usuario gerarUsuario() {
        return Usuario.builder()
                .id(gerarId())
                .nome("User Test")
                .email("emailteste@gmail.com")
                .senha("senhateste123")
                .dataCadastro(gerarData())
                .build();
    }

    public static String gerarJsonUsuario() {
        return "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\":\"senhatesteA123@\"}";
    }
}
