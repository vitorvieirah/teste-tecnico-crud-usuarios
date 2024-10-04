package com.example.user_technical_test.aplication.gateways;

import com.example.user_technical_test.domain.User;

import java.util.Optional;

public interface UserGateway {
    User save(User newData);

    Optional<User> consultById(Long idUser);

    void delete(Long idUser);

    Optional<User> consultByEmail(String email);
}
