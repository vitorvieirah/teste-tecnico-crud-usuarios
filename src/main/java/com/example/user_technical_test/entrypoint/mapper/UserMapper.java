package com.example.user_technical_test.entrypoint.mapper;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.entrypoint.dto.UserDto;
import com.example.user_technical_test.infrastructure.repositories.entities.UserEntity;

public class UserMapper {

    public static User forDomainFromDto(UserDto dto) {
        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .registerDate(dto.dateRegister())
                .build();
    }

    public static UserDto forDto(User domain) {
        return UserDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .dateRegister(domain.getRegisterDate())
                .build();
    }
}
