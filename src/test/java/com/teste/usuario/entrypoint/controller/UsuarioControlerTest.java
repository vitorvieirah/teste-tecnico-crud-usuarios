package com.teste.usuario.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.entrypoint.dto.ResponseDto;
import com.teste.usuario.entrypoint.dto.UsuarioDto;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import com.teste.usuario.infrastructure.security.TokenUseCase;
import com.teste.usuario.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioRepository repository;

    @Autowired
    private TokenUseCase tokenUseCase;

    private UsuarioEntity usuarioTeste;

    private final String USUARIO_JSON = UsuarioBuilder.gerarJsonUsuario();

    @BeforeEach
    void setUp() {
        this.usuarioTeste = UsuarioMapper.paraEntity(UsuarioBuilder.gerarUsuario());
    }

    @Test
    void testeMetodoCadastroUsuario() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any())).thenReturn(usuarioTeste);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String usuarioJson = UsuarioBuilder.gerarJsonUsuario();

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson));

        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isCreated());
        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }



    @Test
    void testeMetodoConsultaPorId() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        String token = tokenUseCase.generateToken(UsuarioMapper.paraDomain(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }


    @Test
    void testeMetodoAlterar() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.save(Mockito.any())).thenReturn(usuarioTeste);

        String token = tokenUseCase.generateToken(UsuarioMapper.paraDomain(usuarioTeste));
        String usuarioJson = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\":\"senhatestenovA@123\"}";

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders
                        .put("/usuarios", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        UsuarioValidator.validaUsuarioController(resultadoRequisicao);
    }

    @Test
    void teteMetodoDeletar() throws Exception {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));

        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());
        String token = tokenUseCase.generateToken(UsuarioMapper.paraDomain(usuarioTeste));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/usuarios/{id}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }
}
