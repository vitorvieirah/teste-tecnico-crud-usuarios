package com.example.user_technical_test.entrypoint.controller;

import com.example.user_technical_test.aplication.usecases.UserUseCase;
import com.example.user_technical_test.entrypoint.dto.UserDto;
import com.example.user_technical_test.entrypoint.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserUseCase useCase;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto newUser) {
        UserDto result = UserMapper.forDto(useCase.register(UserMapper.forDomainFromDto(newUser)));
        return ResponseEntity.created(UriComponentsBuilder.newInstance().path("/usuario/{id}").buildAndExpand(result.id()).toUri())
                .body(result);
    }

    @GetMapping(value = "/consult/{idUser}")
    public ResponseEntity<UserDto> consultById(@PathVariable Long idUser) {
        UserDto result = UserMapper.forDto(useCase.consultById(idUser));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/change")
    public ResponseEntity<UserDto> change(@RequestBody UserDto newData) {
        UserDto result = UserMapper.forDto(useCase.change(UserMapper.forDomainFromDto(newData)));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/delete/{idUser}")
    public ResponseEntity<Void> delete(@PathVariable Long idUser) {
        useCase.delete(idUser);
        return ResponseEntity.noContent().build();
    }
}
