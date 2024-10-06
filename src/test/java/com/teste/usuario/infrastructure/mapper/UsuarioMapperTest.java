package com.teste.usuario.infrastructure.mapper;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import com.teste.usuario.validator.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioMapperTest {

    private final Usuario usuarioTeste = UsuarioBuilder.gerarUsuario();

    @Test
    void converterDomainParaEntity() {
        UsuarioEntity resultado = UsuarioMapper.paraEntity(usuarioTeste);
        Assertions.assertEquals(usuarioTeste.getId(), resultado.getId());
        Assertions.assertEquals(usuarioTeste.getNome(), resultado.getNome());
        Assertions.assertEquals(usuarioTeste.getEmail(), resultado.getEmail());
        Assertions.assertEquals(usuarioTeste.getSenha(), resultado.getSenha());
        Assertions.assertEquals(usuarioTeste.getDataCadastro(), resultado.getDataCadastro());
    }

    @Test
    void converterEntityParaDomain() {
        Usuario resultado = UsuarioMapper.paraDomain(UsuarioMapper.paraEntity(usuarioTeste));
        Assertions.assertEquals(usuarioTeste.getId(), resultado.getId());
        UsuarioValidator.validaUsuario(usuarioTeste, resultado);
        Assertions.assertEquals(usuarioTeste.getDataCadastro(), resultado.getDataCadastro());
    }
}
