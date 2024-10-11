package com.dotmonsservice.kafkasms.kafka;

import com.dotmonsservice.kafkasms.config.KafkaTopicConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaProducer {

    private KafkaTopicConfig kafkaTopicConfig;
    KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public void send() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send(kafkaTopicConfig.getTopicBuilder(), "Tayo is great " + i);
        }
    }

}
