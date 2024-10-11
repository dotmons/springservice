package com.dotmonsservice.twillosms.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("smsmq")
@Data
public class MessageQueueConfig {

    private String queueName;
    private String routingkey;
    private String exchangeName;

}
