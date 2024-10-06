package com.teste.usuario.entrypoint.mapper;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.entrypoint.dto.UsuarioDto;
import com.teste.usuario.validator.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioMapperTest {

    private final Usuario usuarioTeste = UsuarioBuilder.gerarUsuario();

    @Test
    void testeConverterDomainParaDto() {
        UsuarioDto resultado = UsuarioMapper.paraDto(usuarioTeste);
        Assertions.assertEquals(usuarioTeste.getId(), resultado.getId());
        Assertions.assertEquals(usuarioTeste.getNome(), resultado.getNome());
        Assertions.assertEquals(usuarioTeste.getEmail(), resultado.getEmail());
        Assertions.assertEquals(usuarioTeste.getSenha(), resultado.getSenha());
        Assertions.assertEquals(usuarioTeste.getDataCadastro(), resultado.getDataCadastro());
    }

    @Test
    void testeConverterDtoParaDomain() {
        Usuario resultado = UsuarioMapper.paraDomain(UsuarioMapper.paraDto(usuarioTeste));
        Assertions.assertEquals(usuarioTeste.getId(), resultado.getId());
        UsuarioValidator.validaUsuario(usuarioTeste, resultado);
        Assertions.assertEquals(usuarioTeste.getDataCadastro(), resultado.getDataCadastro());
    }
}
