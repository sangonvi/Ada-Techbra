package com.example.demo.controller;

import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    private final Map<String, String> users = Map.of(
        "admin", "123",
        "user", "123"
    );

    private final Map<String, List<String>> roles = Map.of(
        "admin", List.of("ROLE_ADMIN"),
        "user", List.of("ROLE_USER")
    );

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            String token = JwtUtil.generateToken(username, roles.get(username));
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Acesso permitido para ADMIN";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "Acesso permitido para USER e ADMIN";
    }
}
