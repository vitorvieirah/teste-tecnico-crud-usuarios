package com.example.user_technical_test.entrypoint.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(Long id, String nome, String email, String senha, LocalDateTime dataCriacao) {}
