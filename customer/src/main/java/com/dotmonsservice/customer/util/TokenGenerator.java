package com.dotmonsservice.customer.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;



@Configuration
@Component
public class TokenGenerator {

    @Value("${customer.saltValue}")
    private String saltValue;

    private static TokenGenerator instance;

    // Using this here to inject the saltValue since static variable below
    // does not accept injection of value into the variable. Doing this are instance creation
    @PostConstruct
    public void init(){
        instance = this;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String passwordEncoder(String password) {
        return BCrypt.hashpw(password, instance.saltValue);
    }

    public static String passwordDecoder(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
