package com.example.user_technical_test.entrypoint.mapper;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.entrypoint.dto.UserDto;
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
    void testeConverterDomainParaDto() {
        UserDto resultado = UserMapper.forDto(userTest);
        Assertions.assertEquals(userTest.getId(), resultado.id());
        Assertions.assertEquals(userTest.getName(), resultado.name());
        Assertions.assertEquals(userTest.getEmail(), resultado.email());
        Assertions.assertEquals(userTest.getPassword(), resultado.password());
        Assertions.assertEquals(userTest.getRegisterDate(), resultado.dateRegister());
    }

    @Test
    void testeConverterDtoParaDomain() {
        User resultado = UserMapper.forDomainFromDto(UserMapper.forDto(userTest));
        Assertions.assertEquals(userTest.getId(), resultado.getId());
        Assertions.assertEquals(userTest.getName(), resultado.getName());
        Assertions.assertEquals(userTest.getEmail(), resultado.getEmail());
        Assertions.assertEquals(userTest.getPassword(), resultado.getPassword());
        Assertions.assertEquals(userTest.getRegisterDate(), resultado.getRegisterDate());
    }
}
