package com.teste.usuario.aplication.gateways;

import com.teste.usuario.domain.Usuario;

import java.util.Optional;

public interface UsuarioGateway {
    Usuario salvar(Usuario newData);

    Optional<Usuario> consultarPorId(Long idUser);

    void deletar(Long idUser);

    Optional<Usuario> consultarPorEmail(String email);
}
