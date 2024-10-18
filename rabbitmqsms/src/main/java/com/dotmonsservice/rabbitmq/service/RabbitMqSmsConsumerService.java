package com.dotmonsservice.rabbitmq.service;

import com.dotmonsservice.rabbitmq.config.MessagingConfig;
import com.dotmonsservice.rabbitmq.dto.SmsOrder;
import com.dotmonsservice.rabbitmq.dto.TwilloResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@AllArgsConstructor
@Service
public class RabbitMqSmsConsumerService {

    private RabbitTemplate rabbitTemplate;
    private final MessagingConfig messagingConfig;
    private final RestTemplate restTemplate;

    @RabbitListener(queues = "${smsmq.queuename}")
    public void consumeSms(SmsOrder smsOrder) {
        try {
            log.info("Received SMS message : " + smsOrder.getMessage());

            HashMap<String, String> requestHashMap = new HashMap<>();
            log.info("Twillo SMS Message : " + smsOrder.getMessage());
            log.info("Twillo SMS PhoneNumber : " + smsOrder.getPhoneNumber());

            requestHashMap.put("message", smsOrder.getMessage());
            requestHashMap.put("phoneNumber", smsOrder.getPhoneNumber());

            TwilloResponse twilloResponse =
                    restTemplate.postForObject("http://TWILLOSMS/api/v1/smstwillo", requestHashMap, TwilloResponse.class);

            if (!twilloResponse.success()) {
                log.error("Error sending SMS message with error message as {} " , twilloResponse.message());
            }
            else{
                log.info("SMS Message Sent to client with phone number: {}", smsOrder.getPhoneNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
