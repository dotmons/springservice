package com.dotmonsservice.kafkasms.config;

import lombok.Data;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConfigurationProperties("kafkasms")
@Data
public class KafkaTopicConfig {

    private String topicBuilder;

    @Bean
    public NewTopic dotmonsServiceTopic() {
        return TopicBuilder.name(topicBuilder).replicas(3).build();
    }
}
