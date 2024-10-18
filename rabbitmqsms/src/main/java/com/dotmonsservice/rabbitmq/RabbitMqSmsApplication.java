package com.dotmonsservice.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RabbitMqSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqSmsApplication.class, args);
    }
}
