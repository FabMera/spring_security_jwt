package com.fabmera.spring_security.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(@NotEmpty String username, @NotEmpty String password) {


}
