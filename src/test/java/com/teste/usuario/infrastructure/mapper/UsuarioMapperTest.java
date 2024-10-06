package com.teste.usuario.infrastructure.mapper;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
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
    void converterDomainParaEntity() {
        UsuarioEntity resultado = UsuarioMapper.paraEntity(usuarioTest);
        Assertions.assertEquals(usuarioTest.getId(), resultado.getId());
        Assertions.assertEquals(usuarioTest.getNome(), resultado.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), resultado.getSenha());
        Assertions.assertEquals(usuarioTest.getDataCadastro(), resultado.getDataCadastro());
    }

    @Test
    void converterEntityParaDomain() {
        Usuario resultado = UsuarioMapper.paraDomain(UsuarioMapper.paraEntity(usuarioTest));
        Assertions.assertEquals(usuarioTest.getId(), resultado.getId());
        Assertions.assertEquals(usuarioTest.getNome(), resultado.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), resultado.getSenha());
        Assertions.assertEquals(usuarioTest.getDataCadastro(), resultado.getDataCadastro());
    }
}
