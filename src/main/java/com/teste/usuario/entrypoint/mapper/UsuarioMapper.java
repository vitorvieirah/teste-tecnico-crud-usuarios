package com.teste.usuario.entrypoint.mapper;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.entrypoint.dto.UsuarioDto;

public class UsuarioMapper {
    public static Usuario paraDomain(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .dataCadastro(dto.getDataCadastro())
                .build();
    }

    public static UsuarioDto paraDto(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .dataCadastro(usuario.getDataCadastro())
                .build();
    }
}
