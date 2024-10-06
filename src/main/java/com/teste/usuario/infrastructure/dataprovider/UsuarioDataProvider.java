package com.teste.usuario.infrastructure.dataprovider;

import com.teste.usuario.aplication.gateways.UsuarioGateway;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.dataprovider.exceptions.ErroDataProviderException;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class UsuarioDataProvider implements UsuarioGateway {

    private final UsuarioRepository repository;
    private static final String MESSAGE_ERROR_SAVE = "Erro ao salvar usuário.";
    private static final String MESSAGE_ERROR_CONSULT_BY_EMAIL = "Erro ao consultar por email.";
    private static final String MESSAGE_ERROR_CONSULT_BY_ID = "Erro ao consultar por id.";
    private static final String MESSAGE_ERROR_DELETE = "Erro ao deletar usuário.";

    @Override
    public Usuario salvar(Usuario novoUsuario) {
        UsuarioEntity usuarioEntity = UsuarioMapper.paraEntity(novoUsuario);
        try {
            usuarioEntity = repository.save(usuarioEntity);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_SAVE, exception);
            throw new ErroDataProviderException(MESSAGE_ERROR_SAVE, exception.getCause());
        }

        return UsuarioMapper.paraDomain(usuarioEntity);
    }

    @Override
    public Optional<Usuario> consultarPorEmail(String email) {
        Optional<UsuarioEntity> usuarioEntity;
        try {
            usuarioEntity = repository.findByEmail(email);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_CONSULT_BY_EMAIL, exception);
            throw new ErroDataProviderException(MESSAGE_ERROR_CONSULT_BY_EMAIL, exception.getCause());
        }

        return usuarioEntity.map(UsuarioMapper::paraDomain);
    }

    @Override
    public Optional<Usuario> consultarPorId(Long idUsuario) {
        Optional<UsuarioEntity> usuarioEntity;
        try {
            usuarioEntity = repository.findById(idUsuario);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_CONSULT_BY_ID, exception);
            throw new ErroDataProviderException(MESSAGE_ERROR_CONSULT_BY_ID, exception.getCause());
        }
        return usuarioEntity.map(UsuarioMapper::paraDomain);
    }

    @Override
    public void deletar(Long idUsuario) {
        try {
            repository.deleteById(idUsuario);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_DELETE, exception);
            throw new ErroDataProviderException(MESSAGE_ERROR_DELETE, exception.getCause());
        }
    }
}
