package com.example.user_technical_test.aplication.usecases;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.entrypoint.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioUseCase {


    public User register(User forDomainFromDto) {
    }

    public User consultById(Long idUser) {
    }

    public User change(UserDto newData) {
    }

    public void delete(Long idUser) {
    }
}
