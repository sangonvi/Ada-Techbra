package com.example.demo.security.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import com.example.demo.config.ApplicationProperties;
import com.example.demo.model.UserInfo;

public class JwtNoStore implements JwtService {
    private final ApplicationProperties properties;

    public JwtNoStore(ApplicationProperties properties) {
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

        return token;
    }

    @Override
    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(token);
    }
}
