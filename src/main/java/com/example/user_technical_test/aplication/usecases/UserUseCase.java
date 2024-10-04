package com.example.user_technical_test.aplication.usecases;

import com.example.user_technical_test.aplication.exceptions.UserAlreadyRegisteredException;
import com.example.user_technical_test.aplication.exceptions.UserNotFoundException;
import com.example.user_technical_test.aplication.gateways.UserGateway;
import com.example.user_technical_test.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserUseCase {

    private final UserGateway gateway;
    private final EncryptUseCase encryptUseCase;

    public User register(User newData) {
        Optional<User> user = consultByEmail(newData.getEmail());
        user.ifPresent(userOptional -> {
            throw new UserAlreadyRegisteredException();
        });
        newData.setPassword(encryptUseCase.encrypt(newData.getPassword()));
        return gateway.save(newData);
    }

    public User consultById(Long idUser) {
        Optional<User> userResult = gateway.consultById(idUser);
        if (userResult.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userResult.get();
    }

    public User change(User newData) {
        User userExisting = consultById(newData.getId());
        if (newData.getPassword() != null)
            newData.setPassword(encryptUseCase.encrypt(newData.getPassword()));
        userExisting.setData(newData);
        return gateway.save(userExisting);
    }

    public void delete(Long idUser) {
        consultById(idUser);
        gateway.delete(idUser);
    }

    private Optional<User> consultByEmail(String email) {
        return gateway.consultByEmail(email);
    }
}
