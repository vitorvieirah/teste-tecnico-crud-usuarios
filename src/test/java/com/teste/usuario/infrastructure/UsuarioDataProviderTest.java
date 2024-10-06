package com.teste.usuario.infrastructure;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.infrastructure.dataprovider.UsuarioDataProvider;
import com.teste.usuario.infrastructure.dataprovider.exceptions.ErroDataProviderException;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.validator.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioDataProviderTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioDataProvider dataProvider;

    private final Long idTest = UsuarioBuilder.gerarId();

    private final Usuario usuarioTeste = UsuarioBuilder.gerarUsuario();

    @Test
    void testSaveUser() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(UsuarioMapper.paraEntity(usuarioTeste));
        usuarioTeste.setId(null);
        Usuario usuarioResult = dataProvider.salvar(usuarioTeste);
        UsuarioValidator.validaUsuario(usuarioTeste, usuarioResult);
        Assertions.assertNotNull(usuarioResult.getId());
        Assertions.assertEquals(usuarioTeste.getDataCadastro(), usuarioResult.getDataCadastro());
    }

    @Test
    void testSaveException() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);
        ErroDataProviderException exception = Assertions.assertThrows(ErroDataProviderException.class, () -> dataProvider.salvar(usuarioTeste));
        Assertions.assertEquals("Erro ao salvar usuário.", exception.getMessage());
    }

    @Test
    void testConsultByEmail() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(UsuarioMapper.paraEntity(usuarioTeste)));
        Optional<Usuario> userResult = dataProvider.consultarPorEmail(usuarioTeste.getEmail());
        userResult.ifPresent(user -> {
            UsuarioValidator.validaUsuario(usuarioTeste, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(usuarioTeste.getDataCadastro(), user.getDataCadastro());
        });
    }

    @Test
    void testConsultByEmailException() {
        Mockito.when(repository.findByEmail(Mockito.any())).thenThrow(RuntimeException.class);
        ErroDataProviderException exception = Assertions.assertThrows(ErroDataProviderException.class, () -> dataProvider.consultarPorEmail(usuarioTeste.getEmail()));
        Assertions.assertEquals("Erro ao consultar por email.", exception.getMessage());
    }

    @Test
    void testConsultById() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(UsuarioMapper.paraEntity(usuarioTeste)));
        Optional<Usuario> userResult = dataProvider.consultarPorId(idTest);
        userResult.ifPresent(user -> {
            UsuarioValidator.validaUsuario(usuarioTeste, user);
            Assertions.assertEquals(idTest, user.getId());
            Assertions.assertEquals(usuarioTeste.getDataCadastro(), user.getDataCadastro());
        });
    }

    @Test
    void testConsultByIdException() {
        Mockito.when(repository.findById(Mockito.any())).thenThrow(RuntimeException.class);
        ErroDataProviderException exception = Assertions.assertThrows(ErroDataProviderException.class, () -> dataProvider.consultarPorId(idTest));
        Assertions.assertEquals("Erro ao consultar por id.", exception.getMessage());
    }
}
