package com.dotmonsservice.twillosms.service;

import com.dotmonsservice.twillosms.config.TwilioConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TwilloSmsService {

    private final TwilioConfiguration twilioConfiguration;
    //private final MessageQueueConfig messageQueueConfig;


    public String sendSmsNow(String phoneNumber, String message) {

        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());

        Message messages = Message
                .creator(
                        new PhoneNumber("+"+ phoneNumber),
                        new PhoneNumber("+" + twilioConfiguration.getFromNumber()),
                        message
                )
                .create();
        log.info("SMS sent to phone number {}", phoneNumber);
        return messages.getStatus().toString();
    }

//    @RabbitListener(queues = "${smsmq.queuename}")
//    public void consumeMessageFromQueue(String message) throws JsonProcessingException {
//
//        log.info("Message received: with phoneNumber as {}", message);
//        // Create ObjectMapper instance
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(message);
//
//        // Extract phoneNumber and message
//        String phoneNumber = jsonNode.get("phoneNumber").asText();
//        String messageValue = jsonNode.get("message").asText();
//
//        // Pausing SMS sending
//        //sendSmsNow(phoneNumber, messageValue);
//    }
}
