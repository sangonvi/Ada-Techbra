package com.example.demo.security.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.model.UserInfo;
import com.example.demo.security.exceptions.InvalidTokenException;

public class JwtWithStore implements JwtService {
    private final ApplicationProperties properties;
    private final Set<String> tokens = new HashSet<String>();

    public JwtWithStore(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public String generateToken(UserInfo userInfo) {
        String token = Jwts.builder()
                .setSubject(userInfo.username())
                .claim("roles", userInfo.roles())
                .claim("email", userInfo.email())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + properties.getExpirationMs()))
                .signWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                .compact();

        tokens.add(token);
        return token;
    }

    @Override
    public Jws<Claims> validateToken(String token) {
        if (!tokens.contains(token)) {
            throw new InvalidTokenException("Token inválido");
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            tokens.remove(token);
            throw e;
        }
    }
}
