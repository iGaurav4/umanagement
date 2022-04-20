package com.lageraho.springsecurity.model;


import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private String userName;
}
