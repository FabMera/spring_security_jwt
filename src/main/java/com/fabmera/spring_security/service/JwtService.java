package com.fabmera.spring_security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fabmera.spring_security.entity.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserModel userModel) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        try {
            return JWT.create()
                    //Payload
                    .withClaim("permissions", userModel.getAuthorities().toString())//Claim
                    .withClaim("name", userModel.getName())
                    .withClaim("role", userModel.getRole().name())
                    .withIssuedAt(issuedAt) //DATE EMISSION
                    .withExpiresAt(expiration)//DATE EXPIRATION
                    .withSubject(userModel.getUsername()) //Subject
                    //Firma
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getSubject(String token) {
        if (token == null)
            throw new RuntimeException("Token invalido");
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            verifier = JWT.require(algorithm)
                    .build().verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        if (verifier.getSubject() == null)
            throw new RuntimeException("Verifier invalido");
        return verifier.getSubject();
    }
}
