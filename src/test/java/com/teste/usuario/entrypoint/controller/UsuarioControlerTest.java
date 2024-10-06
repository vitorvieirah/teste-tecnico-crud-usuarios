package com.teste.usuario.entrypoint.controller;

import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import com.teste.usuario.validator.UserValidator;
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

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository repository;

    private final UsuarioEntity userTest = UsuarioEntity.builder()
            .id(1L)
            .nome("User Test")
            .email("emailteste@gmail.com")
            .senha("senhateste123")
            .dataCadastro(LocalDateTime.of(2024, 10, 5, 12, 23, 42, 414851900))
            .build();

    private final String USUARIO_JSON = "{\"name\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"password\":\"senhateste123\"}";

    @Test
    void testeMetodoCadastroUsuario() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any())).thenReturn(userTest);

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON));

        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isCreated());
        UserValidator.userValidateController(resultadoRequisicao);
    }

    @Test
    void testeMetodoConsultaPorId() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(userTest));
        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/users/consult/{id}", userTest.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        UserValidator.userValidateController(resultadoRequisicao);
    }

    @Test
    void testeMetodoAlterar() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(userTest));
        Mockito.when(repository.save(Mockito.any())).thenReturn(userTest);

        String usuarioJson = "{\"id\": 1, \"name\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"password\":\"senhateste123\"}";

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        UserValidator.userValidateController(resultadoRequisicao);
    }

    @Test
    void teteMetodoDeletar() throws Exception {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(userTest));

        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }
}
