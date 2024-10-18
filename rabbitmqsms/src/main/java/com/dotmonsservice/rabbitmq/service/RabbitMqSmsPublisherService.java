package com.dotmonsservice.rabbitmq.service;

import com.dotmonsservice.rabbitmq.config.MessagingConfig;
import com.dotmonsservice.rabbitmq.dto.SmsOrder;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RabbitMqSmsPublisherService {

    private RabbitTemplate rabbitTemplate;
    private final MessagingConfig messagingConfig;

    public void publish(SmsOrder smsOrder) {
        try {
            rabbitTemplate.convertAndSend(messagingConfig.getExchangename(), messagingConfig.getRoutingkey(), smsOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
