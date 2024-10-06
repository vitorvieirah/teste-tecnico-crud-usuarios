package com.teste.usuario.validator;

import com.teste.usuario.domain.Usuario;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

public class UserValidator {

    private static final LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 5, 12, 23, 42, 414851900);

    public static void userValidate(Usuario expectedData, Usuario dataReceived) {
        Assertions.assertEquals(expectedData.getNome(), dataReceived.getNome());
        Assertions.assertEquals(expectedData.getEmail(), dataReceived.getEmail());
        Assertions.assertEquals(expectedData.getSenha(), dataReceived.getSenha());
    }

    public static void userValidateController(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User Test"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("emailteste@gmail.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("senhateste123"));
        resultActions.andExpect(result -> {
            String responseContent = result.getResponse().getContentAsString();

            String dateRegisterStr = JsonPath.parse(responseContent).read("$.dateRegister", String.class);

            LocalDateTime actualDate = LocalDateTime.parse(dateRegisterStr);

            Assertions.assertEquals(fixedDate, actualDate);
        });
    }
}
