package com.teste.usuario.infrastructure;

import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.dataprovider.UsuarioDataProvider;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.validator.UserValidator;
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
public class UsuarioDataProviderTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioDataProvider dataProvider;

    private final Long idTest = 1L;

    private final Usuario usuarioTest = Usuario.builder()
            .id(idTest)
            .nome("User Test")
            .email("emailteste@gmail.com")
            .senha("senhateste123")
            .dataCadastro(LocalDateTime.now())
            .build();

    @Test
    void testSaveUser() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(UsuarioMapper.paraEntity(usuarioTest));
        usuarioTest.setId(null);
        Usuario usuarioResult = dataProvider.salvar(usuarioTest);
        UserValidator.userValidate(usuarioTest, usuarioResult);
        Assertions.assertNotNull(usuarioResult.getId());
        Assertions.assertEquals(usuarioTest.getDataCadastro(), usuarioResult.getDataCadastro());
    }

    @Test
    void testSaveException() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorSavingUserException exception = Assertions.assertThrows(ErrorSavingUserException.class, () -> dataProvider.salvar(usuarioTest));
        Assertions.assertEquals("Erro ao salvar usuário", exception.getMessage());
    }

    @Test
    void testConsultByEmail() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(UsuarioMapper.paraEntity(usuarioTest)));
        Optional<Usuario> userResult = dataProvider.consultarPorEmail(usuarioTest.getEmail());
        userResult.ifPresent(user -> {
            UserValidator.userValidate(usuarioTest, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(usuarioTest.getDataCadastro(), user.getDataCadastro());
        });
    }

    @Test
    void testConsultByEmailException() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorConsultUserByEmailException exception = Assertions.assertThrows(ErrorConsultUserByEmailException.class, () -> dataProvider.consultarPorEmail(usuarioTest.getEmail()));
        Assertions.assertEquals("Erro ao consultar por email", exception.getMessage());
    }

    @Test
    void testConsultById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(UsuarioMapper.paraEntity(usuarioTest)));
        Optional<Usuario> userResult = dataProvider.consultarPorId(idTest);
        userResult.ifPresent(user -> {
            UserValidator.userValidate(usuarioTest, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(usuarioTest.getDataCadastro(), user.getDataCadastro());
        });
    }

    @Test
    void testConsultByIdException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(RuntimeException.class);
        ErrorConsultUserByIdException exception = Assertions.assertThrows(ErrorConsultUserByIdException.class, () -> dataProvider.consultarPorId(idTest));
        Assertions.assertEquals("Erro ao consultar por id", exception.getMessage());
    }
}
