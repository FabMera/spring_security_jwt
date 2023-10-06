package com.fabmera.spring_security.controller;

import com.fabmera.spring_security.dto.AuthenticationRequest;
import com.fabmera.spring_security.dto.AuthenticationResponse;
import com.fabmera.spring_security.errors.PasswordAuthenticationException;
import com.fabmera.spring_security.errors.UsuarioNotFoundException;
import com.fabmera.spring_security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationNotSupportedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest, BindingResult binding) throws UsuarioNotFoundException, IOException, PasswordAuthenticationException {
        AuthenticationResponse jwtDTO = null;
        Map<String, Object> response = new HashMap<>();
        if (binding.hasErrors()) {
            return validation(binding);
        }
        try{
            jwtDTO = authenticationService.login(authenticationRequest);
        } catch (UsuarioNotFoundException e) {
            response.put("error:", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        } catch (PasswordAuthenticationException e) {
            response.put("error:", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(jwtDTO);
    }

    //Method test
    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public String publicAccessEndPoint() {
        return "Public access ENDPOINT";
    }

    private ResponseEntity<?> validation(BindingResult binding) {
        Map<String, Object> response = new HashMap<>();
        List<String> errores = binding.getFieldErrors().stream().map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage()).collect(Collectors.toList());
        response.put("errores", errores);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
}
