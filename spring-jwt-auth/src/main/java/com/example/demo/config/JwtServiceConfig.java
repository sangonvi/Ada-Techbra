package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.security.services.JwtNoStore;
import com.example.demo.security.services.JwtService;
import com.example.demo.security.services.JwtWithStore;

@Configuration
public class JwtServiceConfig {
    private final ApplicationProperties properties;

    public JwtServiceConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JwtService jwtService() {
        // return new JwtNoStore(properties);
        return new JwtWithStore(properties);
    }
}