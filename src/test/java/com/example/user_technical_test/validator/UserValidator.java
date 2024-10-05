package com.example.user_technical_test.validator;

import com.example.user_technical_test.domain.User;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;

public class UserValidator {

    public static void userValidate(User expectedData, User dataReceived) {
        Assertions.assertEquals(expectedData.getName(), dataReceived.getName());
        Assertions.assertEquals(expectedData.getEmail(), dataReceived.getEmail());
        Assertions.assertEquals(expectedData.getPassword(), dataReceived.getPassword());
    }

    public static void userValidateController(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User Test"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("emailteste@gmail.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("senhateste123"));
    }
}
