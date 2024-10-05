package com.example.user_technical_test.entrypoint.controller;

import com.example.user_technical_test.aplication.usecases.EncryptUseCase;
import com.example.user_technical_test.domain.User;
import com.example.user_technical_test.infrastructure.mapper.UserMapper;
import com.example.user_technical_test.infrastructure.repositories.UserRepository;
import com.example.user_technical_test.validator.UserValidator;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
public class UserControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private EncryptUseCase encryptUseCase;

    private final LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 5, 12, 23, 42, 414851900);

    private final User userTest = User.builder()
            .id(1L)
            .name("User Test")
            .email("emailteste@gmail.com")
            .password("senhateste123")
            .registerDate(fixedDate)
            .build();

    @Test
    void testeMetodoCadastroUsuario() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any())).thenReturn(UserMapper.forEntity(userTest));
        Mockito.when(encryptUseCase.encrypt(Mockito.any())).thenReturn(userTest.getPassword());

        String usuarioJson = "{\"name\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"password\":\"senhateste123\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson));

        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isCreated());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        resultadoRequisicao.andExpect(result -> {
            String responseContent = result.getResponse().getContentAsString();

            String dateRegisterStr = JsonPath.parse(responseContent).read("$.dateRegister", String.class);

            LocalDateTime actualDate = LocalDateTime.parse(dateRegisterStr);

            Assertions.assertEquals(userTest.getRegisterDate(), actualDate);
        });
        UserValidator.userValidateController(resultadoRequisicao);
    }

    @Test
    void testeMetodoConsutlra


}
