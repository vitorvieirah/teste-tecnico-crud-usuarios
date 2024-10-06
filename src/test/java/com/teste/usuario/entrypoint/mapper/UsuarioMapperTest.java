package com.teste.usuario.entrypoint.mapper;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.entrypoint.dto.UsuarioDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UsuarioMapperTest {

    private final Usuario usuarioTest = Usuario.builder()
            .id(1L)
            .nome("User Test")
            .email("emailteste@gmail.com")
            .senha("senhateste123")
            .dataCadastro(LocalDateTime.now())
            .build();

    @Test
    void testeConverterDomainParaDto() {
        UsuarioDto resultado = UsuarioMapper.paraDto(usuarioTest);
        Assertions.assertEquals(usuarioTest.getId(), resultado.id());
        Assertions.assertEquals(usuarioTest.getNome(), resultado.name());
        Assertions.assertEquals(usuarioTest.getEmail(), resultado.email());
        Assertions.assertEquals(usuarioTest.getSenha(), resultado.password());
        Assertions.assertEquals(usuarioTest.getDataCadastro(), resultado.dateRegister());
    }

    @Test
    void testeConverterDtoParaDomain() {
        Usuario resultado = UsuarioMapper.paraDomain(UsuarioMapper.paraDto(usuarioTest));
        Assertions.assertEquals(usuarioTest.getId(), resultado.getId());
        Assertions.assertEquals(usuarioTest.getNome(), resultado.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), resultado.getSenha());
        Assertions.assertEquals(usuarioTest.getDataCadastro(), resultado.getDataCadastro());
    }
}
