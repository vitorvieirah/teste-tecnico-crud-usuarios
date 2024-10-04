package com.example.user_technical_test.infrastructure.dataprovider;

import com.example.user_technical_test.aplication.gateways.UserGateway;
import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultByEmailException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultByIdException;
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

    @Override
    public User save(User newData) {
        UserEntity userEntity = UserMapper.forEntity(newData);
        try {
            userEntity = repository.save(userEntity);
        } catch (Exception exception) {
            log.error("Erro ao salvar usuário", exception);
            throw new ErrorSavingUserException(exception.getMessage());
        }

        return UserMapper.forDomain(userEntity);
    }

    @Override
    public Optional<User> consultByEmail(String email) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = repository.findByEmail(email);
        } catch (Exception exception) {
            log.error("Erro ao consultar por email", exception);
            throw new ErrorConsultByEmailException(exception.getMessage());
        }

        return userEntity.map(UserMapper::forDomain);
    }

    @Override
    public Optional<User> consultById(Long idUser) {
        Optional<UserEntity> userEntity;
        try {
            userEntity = repository.findById(idUser);
        } catch (Exception exception) {
            log.error("Erro ao consultar por id", exception);
            throw new ErrorConsultByIdException(exception.getMessage());
        }
        return userEntity.map(UserMapper::forDomain);
    }

    @Override
    public void delete(Long idUser) {
        repository.deleteById(idUser);
    }
}
