package com.example.user_technical_test.infrastructure.dataprovider;

import com.example.user_technical_test.aplication.gateways.UserGateway;
import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErroDeleteUserException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultUserByEmailException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultUserByIdException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorSavingUserException;
import com.example.user_technical_test.infrastructure.mapper.UserMapper;
import com.example.user_technical_test.infrastructure.repositories.UserRepository;
import com.example.user_technical_test.infrastructure.repositories.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class UserDataProvider implements UserGateway {

    private final UserRepository repository;
    private static final String MESSAGE_ERROR_SAVE = "Erro ao salvar usuário";
    private static final String MESSAGE_ERROR_CONSULT_BY_EMAIL = "Erro ao consultar por email";
    private static final String MESSAGE_ERROR_CONSULT_BY_ID = "Erro ao consultar por id";
    private static final String MESSAGE_ERROR_DELETE = "Erro ao deletar usuário";

    @Override
    public User save(User newData) {
        UserEntity userEntity = UserMapper.forEntity(newData);
        try {
            userEntity = repository.save(userEntity);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_SAVE, exception);
            throw new ErrorSavingUserException(MESSAGE_ERROR_SAVE);
        }

        return UserMapper.forDomain(userEntity);
    }

    @Override
    public Optional<User> consultByEmail(String email) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = repository.findByEmail(email);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_CONSULT_BY_EMAIL, exception);
            throw new ErrorConsultUserByEmailException(MESSAGE_ERROR_CONSULT_BY_EMAIL);
        }

        return userEntity.map(UserMapper::forDomain);
    }

    @Override
    public Optional<User> consultById(Long idUser) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = repository.findById(idUser);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_CONSULT_BY_ID, exception);
            throw new ErrorConsultUserByIdException(MESSAGE_ERROR_CONSULT_BY_ID);
        }
        return userEntity.map(UserMapper::forDomain);
    }

    @Override
    public void delete(Long idUser) {
        try {
            repository.deleteById(idUser);
        } catch (Exception exception) {
            log.error(MESSAGE_ERROR_DELETE, exception);
            throw new ErroDeleteUserException(MESSAGE_ERROR_DELETE);
        }
    }
}
