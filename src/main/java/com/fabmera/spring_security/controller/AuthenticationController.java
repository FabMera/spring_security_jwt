package com.fabmera.spring_security.controller;

import com.fabmera.spring_security.dto.AuthenticationRequest;
import com.fabmera.spring_security.dto.AuthenticationResponse;
import com.fabmera.spring_security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationNotSupportedException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse jwtDto = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(jwtDto);
    }

    //Method test
    @GetMapping("/public-access")
    public String publicAccessEndPoint() {
        return "Public access ENDPOINT";
    }

}
