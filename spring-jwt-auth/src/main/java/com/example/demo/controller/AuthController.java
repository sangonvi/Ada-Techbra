package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.model.UserInfo;
import com.example.demo.security.services.JwtService;
import com.example.demo.security.services.JwtWithStore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    private final Map<String, UserInfo> users = Map.of(
            "admin", new UserInfo("admin", "123", List.of("ROLE_ADMIN"), "admin@example.com"),
            "user", new UserInfo("user", "123", List.of("ROLE_USER"), "user@example.com"));

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        UserInfo user = users.get(request.username());

        if (user == null || !user.password().equals(request.password())) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/admin")
    public String adminAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "Acesso permitido para ADMIN: " + username;
    }

    @GetMapping("/user")
    public String userAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "Acesso permitido para USER/ADMIN: " + username;
    }

    @GetMapping("/check-thread")
    public String checkThread() {
        String threadName = Thread.currentThread().toString();
        return "Current thread: " + threadName;
    }
}
