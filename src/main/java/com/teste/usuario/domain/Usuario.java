package com.teste.usuario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;

    public void setDados(Usuario newData) {
        this.nome = newData.getNome();
        this.email = newData.getEmail();
        this.senha = newData.getSenha();
    }

    public void setSenhaDataCadastro(String senha, LocalDateTime dataCadastro) {
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }
}
