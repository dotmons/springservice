package com.dotmonsservice.kafkasms.controller;


import com.dotmonsservice.kafkasms.dto.Smsbody;
import com.dotmonsservice.kafkasms.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/sendsmstokafka")
public class KafkaSmsController {

    private KafkaProducerService kafkaProducerService;
    @PostMapping
    public void sendMessage(@RequestBody Smsbody smsbody) {
        log.info("Sending message: {}", smsbody);
        kafkaProducerService.sendSms(smsbody);
        log.info("Sent message: {}", smsbody);
    }
}
