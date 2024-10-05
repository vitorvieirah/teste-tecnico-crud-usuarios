package com.example.user_technical_test.validator;

import com.example.user_technical_test.domain.User;
import org.junit.jupiter.api.Assertions;

public class UserValidator {

    public static void userValidate(User expectedData, User dataReceived) {
        Assertions.assertEquals(expectedData.getName(), dataReceived.getName());
        Assertions.assertEquals(expectedData.getEmail(), dataReceived.getEmail());
        Assertions.assertEquals(expectedData.getPassword(), dataReceived.getPassword());
    }
}
