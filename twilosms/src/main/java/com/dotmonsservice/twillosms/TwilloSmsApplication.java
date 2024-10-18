package com.dotmonsservice.twillosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class TwilloSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwilloSmsApplication.class, args);
    }
}
