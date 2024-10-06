package com.teste.usuario.validator;

import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.domain.Usuario;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

public class UsuarioValidator {

    private static final LocalDateTime dataFixa = UsuarioBuilder.gerarData();

    public static void validaUsuario(Usuario expectedData, Usuario dataReceived) {
        Assertions.assertEquals(expectedData.getNome(), dataReceived.getNome());
        Assertions.assertEquals(expectedData.getEmail(), dataReceived.getEmail());
        Assertions.assertEquals(expectedData.getSenha(), dataReceived.getSenha());
    }

    public static void validaUsuarioController(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User Test"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("emailteste@gmail.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("senhateste123"));
        resultActions.andExpect(result -> {
            String responseContent = result.getResponse().getContentAsString();

            String dateRegisterStr = JsonPath.parse(responseContent).read("$.dateRegister", String.class);

            LocalDateTime actualDate = LocalDateTime.parse(dateRegisterStr);

            Assertions.assertEquals(dataFixa, actualDate);
        });
    }
}
