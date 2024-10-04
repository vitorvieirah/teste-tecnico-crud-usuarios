package com.example.user_technical_test.infrastructure.mapper;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.repositories.entities.UserEntity;

public class UserMapper {

    public static UserEntity forEntity(User newData) {
        return UserEntity.builder()
                .id(newData.getId())
                .name(newData.getName())
                .email(newData.getEmail())
                .password(newData.getPassword())
                .dateRegister(newData.getRegisterDate())
                .build();
    }

    public static User forDomain(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .registerDate(userEntity.getDateRegister())
                .build();
    }
}
