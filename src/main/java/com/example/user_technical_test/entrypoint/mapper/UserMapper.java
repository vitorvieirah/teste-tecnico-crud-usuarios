package com.example.user_technical_test.entrypoint.mapper;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.entrypoint.dto.UserDto;

public class UserMapper {

    public static User forDomainFromDto(UserDto dto) {
        return User.builder()
                .id(dto.id())
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .dataCricao(dto.dataCriacao())
                .build();
    }

    public static UserDto forDto(User domain) {
        return UserDto.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .senha(domain.getSenha())
                .dataCriacao(domain.getDataCricao())
                .build();
    }
}
