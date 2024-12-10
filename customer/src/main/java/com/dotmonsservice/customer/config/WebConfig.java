package com.dotmonsservice.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/customers")
                .allowedOriginPatterns("http://localhost:*") // Allow specific origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow necessary HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true);
    }
}