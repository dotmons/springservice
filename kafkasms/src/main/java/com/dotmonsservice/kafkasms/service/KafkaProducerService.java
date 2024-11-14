package com.dotmonsservice.kafkasms.service;

import com.dotmonsservice.kafkasms.config.KafkaTopicConfig;
import com.dotmonsservice.kafkasms.dto.Smsbody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class KafkaProducerService {

    private KafkaTopicConfig kafkaTopicConfig;
    private KafkaTemplate<String, Smsbody> kafkaTemplate;

    public boolean sendSms(Smsbody smsbody) {
        try {
            if (Objects.nonNull(smsbody.getMessage()) && Objects.nonNull(smsbody.getPhoneNumber()))
            {
                kafkaTemplate.send(kafkaTopicConfig.getTopicBuilder(), smsbody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
