package com.example.user_technical_test.aplication;

import com.example.user_technical_test.aplication.exceptions.UserAlreadyRegisteredException;
import com.example.user_technical_test.aplication.exceptions.UserNotFoundException;
import com.example.user_technical_test.aplication.gateways.UserGateway;
import com.example.user_technical_test.aplication.usecases.EncryptUseCase;
import com.example.user_technical_test.aplication.usecases.UserUseCase;
import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Captor
    ArgumentCaptor<User> captor;

    @Mock
    private UserGateway gateway;

    @Mock
    private EncryptUseCase encryptUseCase;

    @InjectMocks
    private UserUseCase useCase;

    private final Long idTest = 1L;

    private final LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 5, 12, 23, 42);

    private final User userTest = User.builder()
            .id(idTest)
            .name("User Test")
            .email("emailteste@gmail.com")
            .password("senhateste123")
            .registerDate(fixedDate)
            .build();

    @Test
    void testRegistration() {
        userTest.setId(null);
        User userEsperado = User.builder()
                .id(userTest.getId())
                .name(userTest.getName())
                .email(userTest.getEmail())
                .password(userTest.getPassword())
                .registerDate(userTest.getRegisterDate())
                .build();


        Mockito.when(gateway.consultByEmail(userTest.getEmail())).thenReturn(Optional.empty());
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);
        Mockito.when(encryptUseCase.encrypt(userTest.getPassword())).thenReturn(userTest.getPassword());

        useCase.register(userTest);
        User userResult = captor.getValue();

        Assertions.assertNull(userResult.getId());
        Assertions.assertEquals(userEsperado.getName(), userResult.getName());
        Assertions.assertEquals(userEsperado.getEmail(), userResult.getEmail());
        Assertions.assertNotNull(userResult.getRegisterDate());
        Assertions.assertEquals(userEsperado.getPassword(), userResult.getPassword());
    }

    @Test
    void testExistingUserRegistration() {
        Mockito.when(gateway.consultByEmail(userTest.getEmail())).thenReturn(Optional.of(userTest));
        UserAlreadyRegisteredException exeception = Assertions.assertThrows(UserAlreadyRegisteredException.class, () -> useCase.register(userTest));
        Assertions.assertEquals("Usuário com este email já cadastrado", exeception.getMessage());
    }

    @Test
    void testQueryById() {
        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        User userResult = Assertions.assertDoesNotThrow(() -> useCase.consultById(idTest));
        Assertions.assertEquals(idTest, userResult.getId());
        UserValidator.userValidate(userTest, userResult);
        Mockito.verify(gateway, Mockito.times(1)).consultById(idTest);
    }

    @Test
    void testQueryNonExistentUseById() {
        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.empty());
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> useCase.consultById(idTest));
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testChangesUserData() {
        User newUserTest = User.builder()
                .id(idTest)
                .name("Novo nome teste")
                .email("novoemailtete@gmail.com")
                .password("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(encryptUseCase.encrypt(newUserTest.getPassword())).thenReturn(newUserTest.getPassword());
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        UserValidator.userValidate(newUserTest, userResult);
    }

    @Test
    void testChangesToUserDataWithNullName() {
        User newUserTest = User.builder()
                .id(idTest)
                .email("novoemailtete@gmail.com")
                .password("novasenhasecretateste123")
                .build();
        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(encryptUseCase.encrypt(newUserTest.getPassword())).thenReturn(newUserTest.getPassword());
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(userTest.getName(), userResult.getName());
        Assertions.assertEquals(newUserTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(newUserTest.getPassword(), userResult.getPassword());
    }

    @Test
    void testChangesToUsersDataWithNullEmail() {
        User newUserTest = User.builder()
                .id(idTest)
                .name("Novo nome teste")
                .password("novasenhasecretateste123")
                .build();
        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(encryptUseCase.encrypt(newUserTest.getPassword())).thenReturn(newUserTest.getPassword());
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(newUserTest.getName(), userResult.getName());
        Assertions.assertEquals(userTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(newUserTest.getPassword(), userResult.getPassword());
    }

    @Test
    void testChangesToUsersDataWithNullPassword() {
        User newUserTest = User.builder()
                .id(idTest)
                .name("Novo nome teste")
                .email("novoemailtete@gmail.com")
                .build();

        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(newUserTest.getName(), userResult.getName());
        Assertions.assertEquals(newUserTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(userTest.getPassword(), userResult.getPassword());
    }

    @Test
    void testChangesToUserDataWithJustAName() {
        User newUserTest = User.builder()
                .id(idTest)
                .name("Novo nome teste")
                .build();

        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(newUserTest.getName(), userResult.getName());
        Assertions.assertEquals(userTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(userTest.getPassword(), userResult.getPassword());
    }

    @Test
    void testChangesToUserDataWithJustAEmail() {
        User newUserTest = User.builder()
                .id(idTest)
                .email("novoemailtete@gmail.com")
                .build();

        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(userTest.getName(), userResult.getName());
        Assertions.assertEquals(newUserTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(userTest.getPassword(), userResult.getPassword());
    }

    @Test
    void testChangesToUserDataWithJustAPassword() {
        User newUserTest = User.builder()
                .id(idTest)
                .password("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultById(idTest)).thenReturn(Optional.of(userTest));
        Mockito.when(encryptUseCase.encrypt(newUserTest.getPassword())).thenReturn(newUserTest.getPassword());
        Mockito.when(gateway.save(captor.capture())).thenReturn(userTest);

        useCase.change(newUserTest);

        User userResult = captor.getValue();
        Assertions.assertEquals(idTest, userResult.getId());
        Assertions.assertEquals(userTest.getName(), userResult.getName());
        Assertions.assertEquals(userTest.getEmail(), userResult.getEmail());
        Assertions.assertEquals(newUserTest.getPassword(), userResult.getPassword());
    }


}
