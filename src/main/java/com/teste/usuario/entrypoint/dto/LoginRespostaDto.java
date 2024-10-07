package com.teste.usuario.entrypoint.dto;

import lombok.Builder;

@Builder
public record LoginRespostaDto(String nome, String token) {
}
