package com.fabmera.spring_security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    String username;
    String password;
}
