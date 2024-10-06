package com.teste.usuario.aplication.exceptions;

public class UsuarioJaCadastradoComEmailException extends RuntimeException {
    public UsuarioJaCadastradoComEmailException() {
        super("Usuário com este email já cadastrado");
    }
}
