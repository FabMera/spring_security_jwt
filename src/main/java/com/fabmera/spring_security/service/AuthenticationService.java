package com.fabmera.spring_security.service;

import com.fabmera.spring_security.dto.AuthenticationRequest;
import com.fabmera.spring_security.dto.AuthenticationResponse;
import com.fabmera.spring_security.entity.UserModel;
import com.fabmera.spring_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {
    //Recibe un AuthenticationRequest y devuelve un AuthenticationResponse con el token.

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticationManager.authenticate(authToken);
        UserModel user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateClaims(user));
        return new AuthenticationResponse(jwt);

    }

    private Map<String, Object> generateClaims(UserModel userModel) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", userModel.getName());
        extraClaims.put("role", userModel.getRole().name());
        return extraClaims;
    }
}
