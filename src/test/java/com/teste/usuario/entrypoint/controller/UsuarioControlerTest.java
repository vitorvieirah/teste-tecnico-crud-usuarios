package com.teste.usuario.entrypoint.controller;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import com.teste.usuario.validator.UsuarioValidator;
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
public class UsuarioControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository repository;

    private final UsuarioEntity userTeste = UsuarioMapper.paraEntity(UsuarioBuilder.gerarUsuario());

    private final String USUARIO_JSON = UsuarioBuilder.gerarJsonUsuario();

    @Test
    void testeMetodoCadastroUsuario() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any())).thenReturn(userTeste);

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON));

        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isCreated());
        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }

    @Test
    void testeMetodoConsultaPorId() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(userTeste));
        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/users/consult/{id}", userTeste.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }

    @Test
    void testeMetodoAlterar() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(userTeste));
        Mockito.when(repository.save(Mockito.any())).thenReturn(userTeste);

        String usuarioJson = "{\"id\": 1, \"name\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"password\":\"senhateste123\"}";

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }

    @Test
    void teteMetodoDeletar() throws Exception {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(userTeste));

        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }
}
