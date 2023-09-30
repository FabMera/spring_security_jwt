package com.fabmera.spring_security.service;

import com.fabmera.spring_security.entity.UserModel;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("$security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("$security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserModel userModel, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        Jwts.builder()
                //Payload
                .setClaims(extraClaims)
                .setSubject(userModel.getUsername())
                .setIssuedAt(issuedAt) //DATE EMISSION
                .setExpiration(expiration)//DATE EXPIRATION
                //Firma
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key generateKey() {
        //Convertimos el secret key a bytes ya que viene en base64,para mas seguridad.
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

}
