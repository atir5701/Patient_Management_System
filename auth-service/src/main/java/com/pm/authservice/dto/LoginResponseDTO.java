package com.pm.authservice.dto;

import java.util.Optional;

public class LoginResponseDTO {
    private final String token;

    public LoginResponseDTO(String token) {this.token = token;}

    public String getToken() {return token;}
}
