package com.teste.usuario.aplication.usecases;

import com.teste.usuario.aplication.exceptions.UsuarioJaCadastradoComEmailException;
import com.teste.usuario.aplication.exceptions.UsuarioNaoEncontradoException;
import com.teste.usuario.aplication.gateways.UsuarioGateway;
import com.teste.usuario.domain.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway gateway;
    private final CriptografiaUseCase criptografiaUseCase;

    public Usuario cadastrar(Usuario novoUsuario) {
        Optional<Usuario> usuarioExistente = gateway.consultarPorEmail(novoUsuario.getEmail());
        usuarioExistente.ifPresent(usuarioOptional -> {
            throw new UsuarioJaCadastradoComEmailException();
        });
        String senhaCriptografada = criptografiaUseCase.criptografar(novoUsuario.getSenha());
        novoUsuario.setSenhaDataCadastro(senhaCriptografada, LocalDateTime.now());
        return gateway.salvar(novoUsuario);
    }

    public Usuario consultarPorId(Long idUsuario) {
        Optional<Usuario> usuario = gateway.consultarPorId(idUsuario);
        if (usuario.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }
        return usuario.get();
    }

    public Usuario alterar(Usuario novosDados, Long idUsuario) {
        Usuario usuarioExistente = consultarPorId(idUsuario);
        Optional<Usuario> usuarioExistenteEmail = gateway.consultarPorEmail(novosDados.getEmail());

        usuarioExistenteEmail.ifPresent(usuarioOptional -> {
            throw new UsuarioJaCadastradoComEmailException();
        });

        novosDados.setSenha(criptografiaUseCase.criptografar(novosDados.getSenha()));
        usuarioExistente.setDados(novosDados);
        return gateway.salvar(usuarioExistente);
    }

    public void deletar(Long idUsuario) {
        consultarPorId(idUsuario);
        gateway.deletar(idUsuario);
    }

    public Usuario consultarPorEmail(String email) {
        Optional<Usuario> usuario = gateway.consultarPorEmail(email);

        if (usuario.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        return usuario.get();
    }
}
