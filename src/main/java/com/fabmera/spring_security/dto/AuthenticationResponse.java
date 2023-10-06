package com.fabmera.spring_security.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthenticationResponse {
    public String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
