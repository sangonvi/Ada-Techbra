package com.example.demo.model;

import java.util.List;

public record UserInfo(
        String username,
        String password,
        List<String> roles,
        String email) {
}
