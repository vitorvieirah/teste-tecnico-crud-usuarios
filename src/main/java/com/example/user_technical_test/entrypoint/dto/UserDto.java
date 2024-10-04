package com.example.user_technical_test.entrypoint.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(Long id, String name, String email, String password, LocalDateTime dateRegister) {}
