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

    public static UsuarioDto paraDto(Usuario domain) {
        return UsuarioDto.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .senha(domain.getSenha())
                .dataCadastro(domain.getDataCadastro())
                .build();
    }
}
