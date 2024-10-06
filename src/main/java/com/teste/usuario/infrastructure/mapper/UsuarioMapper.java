package com.teste.usuario.infrastructure.mapper;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity paraEntity(Usuario domain) {
        return UsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .senha(domain.getSenha())
                .dataCadastro(domain.getDataCadastro())
                .build();
    }

    public static Usuario paraDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .dataCadastro(entity.getDataCadastro())
                .build();
    }
}
