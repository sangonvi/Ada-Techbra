package com.example.demo.security.services;

import com.example.demo.model.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {

    String generateToken(UserInfo userInfo);

    Jws<Claims> validateToken(String token);
}