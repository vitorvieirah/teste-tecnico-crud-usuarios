package com.example.user_technical_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime registerDate;

    public void setData(User newData) {
        if(newData.getName() != null)
            this.name = newData.getName();
        if(newData.getEmail() != null)
            this.email = newData.getEmail();
        if(newData.getPassword() != null)
            this.password = newData.getPassword();
    }
}
