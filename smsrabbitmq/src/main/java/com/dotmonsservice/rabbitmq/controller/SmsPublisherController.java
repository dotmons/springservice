package com.dotmonsservice.rabbitmq.controller;


import com.dotmonsservice.rabbitmq.config.MessagingConfig;
import com.dotmonsservice.rabbitmq.dto.SmsOrder;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/smspublisher")
@AllArgsConstructor
public class SmsPublisherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final MessagingConfig messagingConfig;

    @PostMapping()
    public String smsOrder(@RequestBody SmsOrder smsOrder){

        rabbitTemplate.convertAndSend(messagingConfig.getExchangename(), messagingConfig.getRoutingkey(), smsOrder);
        return "Message sent successfully";

    }
}
