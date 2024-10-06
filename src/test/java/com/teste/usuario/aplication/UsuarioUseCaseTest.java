package com.teste.usuario.aplication;

import com.teste.usuario.aplication.exceptions.UsuarioJaCadastradoComEmailException;
import com.teste.usuario.aplication.exceptions.UsuarioNaoEncontradoException;
import com.teste.usuario.aplication.gateways.UsuarioGateway;
import com.teste.usuario.aplication.usecases.CriptografiaUseCase;
import com.teste.usuario.aplication.usecases.UsuarioUseCase;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioUseCaseTest {

    @Captor
    ArgumentCaptor<Usuario> captor;

    @Mock
    private UsuarioGateway gateway;

    @Mock
    private CriptografiaUseCase criptografiaUseCase;

    @InjectMocks
    private UsuarioUseCase useCase;

    private final Long idTest = 1L;

    private final LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 5, 12, 23, 42);

    private final Usuario usuarioTest = Usuario.builder()
            .id(idTest)
            .nome("User Test")
            .email("emailteste@gmail.com")
            .senha("senhateste123")
            .dataCadastro(fixedDate)
            .build();

    @Test
    void testRegistration() {
        usuarioTest.setId(null);
        Usuario usuarioEsperado = Usuario.builder()
                .id(usuarioTest.getId())
                .nome(usuarioTest.getNome())
                .email(usuarioTest.getEmail())
                .senha(usuarioTest.getSenha())
                .dataCadastro(usuarioTest.getDataCadastro())
                .build();


        Mockito.when(gateway.consultarPorEmail(usuarioTest.getEmail())).thenReturn(Optional.empty());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);
        Mockito.when(criptografiaUseCase.criptografar(usuarioTest.getSenha())).thenReturn(usuarioTest.getSenha());

        useCase.cadastrar(usuarioTest);
        Usuario usuarioResult = captor.getValue();

        Assertions.assertNull(usuarioResult.getId());
        Assertions.assertEquals(usuarioEsperado.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(usuarioEsperado.getEmail(), usuarioResult.getEmail());
        Assertions.assertNotNull(usuarioResult.getDataCadastro());
        Assertions.assertEquals(usuarioEsperado.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testExistingUserRegistration() {
        Mockito.when(gateway.consultarPorEmail(usuarioTest.getEmail())).thenReturn(Optional.of(usuarioTest));
        UsuarioJaCadastradoComEmailException exeception = Assertions.assertThrows(UsuarioJaCadastradoComEmailException.class, () -> useCase.cadastrar(usuarioTest));
        Assertions.assertEquals("Usuário com este email já cadastrado", exeception.getMessage());
    }

    @Test
    void testQueryById() {
        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Usuario usuarioResult = Assertions.assertDoesNotThrow(() -> useCase.consultarPorId(idTest));
        Assertions.assertEquals(idTest, usuarioResult.getId());
        UserValidator.userValidate(usuarioTest, usuarioResult);
        Mockito.verify(gateway, Mockito.times(1)).consultarPorId(idTest);
    }

    @Test
    void testQueryNonExistentUseById() {
        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.empty());
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.consultarPorId(idTest));
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testChangesUserData() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .nome("Novo nome teste")
                .email("novoemailtete@gmail.com")
                .senha("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(criptografiaUseCase.criptografar(newUsuarioTest.getSenha())).thenReturn(newUsuarioTest.getSenha());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest, idTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        UserValidator.userValidate(newUsuarioTest, usuarioResult);
    }

    @Test
    void testChangesToUserDataWithNullName() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .email("novoemailtete@gmail.com")
                .senha("novasenhasecretateste123")
                .build();
        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(criptografiaUseCase.criptografar(newUsuarioTest.getSenha())).thenReturn(newUsuarioTest.getSenha());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest, idTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(usuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(newUsuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(newUsuarioTest.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testChangesToUsersDataWithNullEmail() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .nome("Novo nome teste")
                .senha("novasenhasecretateste123")
                .build();
        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(criptografiaUseCase.criptografar(newUsuarioTest.getSenha())).thenReturn(newUsuarioTest.getSenha());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest, idTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(newUsuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(newUsuarioTest.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testChangesToUsersDataWithNullPassword() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .nome("Novo nome teste")
                .email("novoemailtete@gmail.com")
                .build();

        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(newUsuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(newUsuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testChangesToUserDataWithJustAName() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .nome("Novo nome teste")
                .build();

        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(newUsuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testChangesToUserDataWithJustAEmail() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .email("novoemailtete@gmail.com")
                .build();

        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(usuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(newUsuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(usuarioTest.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testChangesToUserDataWithJustAPassword() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTest)
                .senha("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultarPorId(idTest)).thenReturn(Optional.of(usuarioTest));
        Mockito.when(criptografiaUseCase.criptografar(newUsuarioTest.getSenha())).thenReturn(newUsuarioTest.getSenha());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTest);

        useCase.alterar(newUsuarioTest);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTest, usuarioResult.getId());
        Assertions.assertEquals(usuarioTest.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(usuarioTest.getEmail(), usuarioResult.getEmail());
        Assertions.assertEquals(newUsuarioTest.getSenha(), usuarioResult.getSenha());
    }


}
