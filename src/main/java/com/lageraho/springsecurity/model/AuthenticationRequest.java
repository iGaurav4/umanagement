package com.lageraho.springsecurity.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @NotNull( message = "Username can not be empty")
    @NotEmpty( message = "Username can not be empty")
    private String username;
    @NotNull(message = "Password can not be empty")
    @NotEmpty(message = "Password can not be empty")
    private String password;
}
