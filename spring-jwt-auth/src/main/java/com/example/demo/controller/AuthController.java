package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        if (users.containsKey(request.username()) && users.get(request.username()).equals(request.password())) {
            String token = JwtUtil.generateToken(request.username(), roles.get(request.username()));
            return ResponseEntity.ok(new LoginResponseDTO(token));
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
