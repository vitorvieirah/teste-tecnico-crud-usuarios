package com.example.user_technical_test.infrastructure;

import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.dataprovider.UserDataProvider;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultUserByEmailException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorConsultUserByIdException;
import com.example.user_technical_test.infrastructure.dataprovider.exceptions.ErrorSavingUserException;
import com.example.user_technical_test.infrastructure.mapper.UserMapper;
import com.example.user_technical_test.infrastructure.repositories.UserRepository;
import com.example.user_technical_test.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserDataProviderTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDataProvider dataProvider;

    private final Long idTest = 1L;

    private final User userTest = User.builder()
            .id(idTest)
            .name("User Test")
            .email("emailteste@gmail.com")
            .password("senhateste123")
            .registerDate(LocalDateTime.now())
            .build();

    @Test
    void testSaveUser() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(UserMapper.forEntity(userTest));
        userTest.setId(null);
        User userResult = dataProvider.save(userTest);
        UserValidator.userValidate(userTest, userResult);
        Assertions.assertNotNull(userResult.getId());
        Assertions.assertEquals(userTest.getRegisterDate(), userResult.getRegisterDate());
    }

    @Test
    void testSaveException() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorSavingUserException exception = Assertions.assertThrows(ErrorSavingUserException.class, () -> dataProvider.save(userTest));
        Assertions.assertEquals("Erro ao salvar usuário", exception.getMessage());
    }

    @Test
    void testConsultByEmail() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(UserMapper.forEntity(userTest)));
        Optional<User> userResult = dataProvider.consultByEmail(userTest.getEmail());
        userResult.ifPresent(user -> {
            UserValidator.userValidate(userTest, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(userTest.getRegisterDate(), user.getRegisterDate());
        });
    }

    @Test
    void testConsultByEmailException() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorConsultUserByEmailException exception = Assertions.assertThrows(ErrorConsultUserByEmailException.class, () -> dataProvider.consultByEmail(userTest.getEmail()));
        Assertions.assertEquals("Erro ao consultar por email", exception.getMessage());
    }

    @Test
    void testConsultById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(UserMapper.forEntity(userTest)));
        Optional<User> userResult = dataProvider.consultById(idTest);
        userResult.ifPresent(user -> {
            UserValidator.userValidate(userTest, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(userTest.getRegisterDate(), user.getRegisterDate());
        });
    }

    @Test
    void testConsultByIdException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorConsultUserByIdException exception = Assertions.assertThrows(ErrorConsultUserByIdException.class, () -> dataProvider.consultById(idTest));
        Assertions.assertEquals("Erro ao consultar por id", exception.getMessage());
    }
}
