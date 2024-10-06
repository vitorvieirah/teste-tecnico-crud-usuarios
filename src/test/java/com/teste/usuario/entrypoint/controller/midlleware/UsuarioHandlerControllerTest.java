package com.teste.usuario.entrypoint.controller.midlleware;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import com.teste.usuario.infrastructure.security.TokenUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository repository;

    @Autowired
    private TokenUseCase tokenUseCase;

    private final UsuarioEntity usuarioTeste = UsuarioMapper.paraEntity(UsuarioBuilder.gerarUsuario());

    private final String USUARIO_JSON = UsuarioBuilder.gerarJsonUsuario();


    @Test
    void testeExceptionUsuarioJaCadastroComEmail() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.save(Mockito.any())).thenReturn(usuarioTeste);

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON));


        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());

        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagem").value("Usuário com este email já cadastrado"));
    }

    @Test
    void testeExceptionUsuarioNaoEncontrado() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        String token = tokenUseCase.generateToken(UsuarioMapper.paraDomain(usuarioTeste));

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


    }


}
