package com.dotmonsservice.customer.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class TokenGenerator {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String passwordEncoder(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
