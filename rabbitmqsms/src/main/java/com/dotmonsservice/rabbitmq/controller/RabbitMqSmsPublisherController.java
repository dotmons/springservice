package com.dotmonsservice.rabbitmq.controller;

import com.dotmonsservice.rabbitmq.dto.SmsOrder;
import com.dotmonsservice.rabbitmq.service.RabbitMqSmsPublisherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/smspublisher")
@AllArgsConstructor
public class RabbitMqSmsPublisherController {

    private RabbitMqSmsPublisherService smsPublisher;

    @PostMapping()
    public String smsOrder(@RequestBody SmsOrder smsOrder){
        log.info("RabbitMQ message received: {}", smsOrder);
        smsPublisher.publish(smsOrder);
        return "Message sent successfully";

    }
}
