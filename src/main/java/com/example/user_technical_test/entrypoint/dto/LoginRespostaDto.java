package com.example.user_technical_test.entrypoint.dto;

import lombok.Builder;

@Builder
public record LoginRespostaDto (String nome, String token) {}
