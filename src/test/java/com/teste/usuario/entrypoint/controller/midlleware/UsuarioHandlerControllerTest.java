package com.teste.usuario.entrypoint.controller.midlleware;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
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

    private final UsuarioEntity userTeste = UsuarioMapper.paraEntity(UsuarioBuilder.gerarUsuario());

    private final String USUARIO_JSON = UsuarioBuilder.gerarJsonUsuario();


    @Test
    void testeExceptionUsuarioJaCadastroComEmail() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(userTeste));
        Mockito.when(repository.save(Mockito.any())).thenReturn(userTeste);

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON));


        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());

        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuário com este email já cadastrado"));
    }

    @Test
    void testeExceptionUsuarioNaoEncontrado() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", userTeste.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Usuário não encontrado"));
    }


}
