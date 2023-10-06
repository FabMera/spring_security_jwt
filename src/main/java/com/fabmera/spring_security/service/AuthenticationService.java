package com.fabmera.spring_security.service;

import com.fabmera.spring_security.dto.AuthenticationRequest;
import com.fabmera.spring_security.dto.AuthenticationResponse;
import com.fabmera.spring_security.entity.UserModel;
import com.fabmera.spring_security.errors.PasswordAuthenticationException;
import com.fabmera.spring_security.errors.UsuarioNotFoundException;
import com.fabmera.spring_security.mapper.UserMapper;
import com.fabmera.spring_security.repository.UserRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    //Recibe un AuthenticationRequest y devuelve un AuthenticationResponse con el token.

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws UsuarioNotFoundException, PasswordAuthenticationException {

        Optional<UserModel> userModel = userRepository.findByUsername(authenticationRequest.username());
        if (userModel.isEmpty()) {
            throw new UsuarioNotFoundException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(authenticationRequest.password(), userModel.get().getPassword())) {
            throw new PasswordAuthenticationException("Contraseña incorrecta");
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password(), userModel.get().getAuthorities());
        authenticationManager.authenticate(authToken);
        String jwt = jwtService.generateToken(userModel.get());
        return new AuthenticationResponse(jwt);
    }

    //
    @Transactional
    private Map<String, Object> generateClaims(UserModel userModel) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", userModel.getName());
        extraClaims.put("role", userModel.getRole().name());
        extraClaims.put("permissions", userModel.getAuthorities());
        return extraClaims;
    }
}
