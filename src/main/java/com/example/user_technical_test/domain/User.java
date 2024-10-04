package com.example.user_technical_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class User {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataCricao;
}
