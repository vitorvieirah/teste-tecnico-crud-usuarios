package com.example.user_technical_test.infrastructure.mapper;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.repositories.entities.UserEntity;
import com.example.user_technical_test.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserMapperTest {

    private final User userTest = User.builder()
            .id(1L)
            .name("User Test")
            .email("emailteste@gmail.com")
            .password("senhateste123")
            .registerDate(LocalDateTime.now())
            .build();

    @Test
    void converterDomainParaEntity() {
        UserEntity resultado = UserMapper.forEntity(userTest);
        Assertions.assertEquals(userTest.getId(), resultado.getId());
        Assertions.assertEquals(userTest.getName(), resultado.getName());
        Assertions.assertEquals(userTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(userTest.getPassword(), resultado.getPassword());
        Assertions.assertEquals(userTest.getRegisterDate(), resultado.getDateRegister());
    }

    @Test
    void converterEntityParaDomain() {
        User resultado = UserMapper.forDomain(UserMapper.forEntity(userTest));
        Assertions.assertEquals(userTest.getId(), resultado.getId());
        Assertions.assertEquals(userTest.getName(), resultado.getName());
        Assertions.assertEquals(userTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(userTest.getPassword(), resultado.getPassword());
        Assertions.assertEquals(userTest.getRegisterDate(), resultado.getRegisterDate());
    }
}
