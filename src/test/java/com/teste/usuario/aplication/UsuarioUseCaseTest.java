package com.teste.usuario.aplication;

import com.teste.usuario.aplication.exceptions.UsuarioJaCadastradoComEmailException;
import com.teste.usuario.aplication.exceptions.UsuarioNaoEncontradoException;
import com.teste.usuario.aplication.gateways.UsuarioGateway;
import com.teste.usuario.aplication.usecases.CriptografiaUseCase;
import com.teste.usuario.aplication.usecases.UsuarioUseCase;
import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.validator.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private final Usuario usuarioTeste = UsuarioBuilder.gerarUsuario();
    private final Long idTeste = UsuarioBuilder.gerarId();

    @Test
    void testeCadastroDeUsuario() {
        usuarioTeste.setId(null);

        Mockito.when(gateway.consultarPorEmail(usuarioTeste.getEmail())).thenReturn(Optional.empty());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTeste);
        Mockito.when(criptografiaUseCase.criptografar(usuarioTeste.getSenha())).thenReturn(usuarioTeste.getSenha());

        useCase.cadastrar(usuarioTeste);
        Usuario usuarioResult = captor.getValue();

        Assertions.assertNull(usuarioResult.getId());
        Assertions.assertEquals(usuarioTeste.getNome(), usuarioResult.getNome());
        Assertions.assertEquals(usuarioTeste.getEmail(), usuarioResult.getEmail());
        Assertions.assertNotNull(usuarioResult.getDataCadastro());
        Assertions.assertEquals(usuarioTeste.getSenha(), usuarioResult.getSenha());
    }

    @Test
    void testeCadastroDeUsuarioJaExistente() {
        Mockito.when(gateway.consultarPorEmail(usuarioTeste.getEmail())).thenReturn(Optional.of(usuarioTeste));
        UsuarioJaCadastradoComEmailException exeception = Assertions.assertThrows(UsuarioJaCadastradoComEmailException.class, () -> useCase.cadastrar(usuarioTeste));
        Assertions.assertEquals("Usuário com este email já cadastrado", exeception.getMessage());
    }

    @Test
    void testeConsultaUsuarioPorId() {
        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.of(usuarioTeste));
        Usuario usuarioResult = Assertions.assertDoesNotThrow(() -> useCase.consultarPorId(idTeste));
        Assertions.assertEquals(idTeste, usuarioResult.getId());
        UsuarioValidator.validaUsuario(usuarioTeste, usuarioResult);
        Mockito.verify(gateway, Mockito.times(1)).consultarPorId(idTeste);
    }

    @Test
    void testeNenhumUsuarioEncontrado() {
        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.empty());
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.consultarPorId(idTeste));
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testeAlteracaoDeDadosUsuario() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTeste)
                .nome("Novo nome teste")
                .email("novoemailtete@gmail.com")
                .senha("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(gateway.consultarPorEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(gateway.salvar(captor.capture())).thenReturn(usuarioTeste);
        Mockito.when(criptografiaUseCase.criptografar(newUsuarioTest.getSenha())).thenReturn(newUsuarioTest.getSenha());

        useCase.alterar(newUsuarioTest, idTeste);

        Usuario usuarioResult = captor.getValue();
        Assertions.assertEquals(idTeste, usuarioResult.getId());
        UsuarioValidator.validaUsuario(newUsuarioTest, usuarioResult);
    }

    @Test
    void testeAlteracaoDeDadosUsuarioNaoEncontrado() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTeste)
                .nome("Novo nome teste")
                .email("emailteste@gmail.com")
                .senha("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.alterar(newUsuarioTest, idTeste));
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testeAlteracaoDeDadosUsuarioComEmailJaExistente() {
        Usuario newUsuarioTest = Usuario.builder()
                .id(idTeste)
                .nome("Novo nome teste")
                .email("emailteste@gmail.com")
                .senha("novasenhasecretateste123")
                .build();

        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(gateway.consultarPorEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));

        UsuarioJaCadastradoComEmailException exception = Assertions.assertThrows(UsuarioJaCadastradoComEmailException.class, () -> useCase.alterar(newUsuarioTest, idTeste));
        Assertions.assertEquals("Usuário com este email já cadastrado", exception.getMessage());
    }


    @Test
    void testeDeletarUsuario() {
        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.of(usuarioTeste));
        Assertions.assertDoesNotThrow(() -> useCase.deletar(idTeste));
        Mockito.verify(gateway, Mockito.times(1)).deletar(idTeste);
    }

    @Test
    void testeDeletarUsuarioNaoExistente() {
        Mockito.when(gateway.consultarPorId(idTeste)).thenReturn(Optional.empty());
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.deletar(idTeste));
        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
