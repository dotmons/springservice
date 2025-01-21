package com.dotmonsservice.kafkasms.service;

import com.dotmonsservice.kafkasms.dto.TwilloResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;


@Slf4j
@Service
public class KafkaConsumerService {

    private final RestTemplate restTemplate;

    @Value("${kafkasms.uri}")
    private String uri;

    public KafkaConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "${kafkasms.topicbuilder}", groupId = "${kafkasms.groupid}")
    void listener(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            // Accessing values
            String message = jsonObject.getString("message");
            String phoneNumber = jsonObject.getString("phoneNumber");

            log.info("Message from kafka: {} and the phone number is: {}", message, phoneNumber);

            if (Objects.nonNull(phoneNumber) && Objects.nonNull(message)) {
                HashMap<String, String> requestHashMap = new HashMap<>();
                requestHashMap.put("phoneNumber", phoneNumber);
                requestHashMap.put("message", message);

                TwilloResponse twilloResponse =
                        restTemplate.postForObject(uri, requestHashMap, TwilloResponse.class);

                if (Objects.nonNull(twilloResponse) && (twilloResponse.success())) {
                    log.info("Successfully posted to Twillo API");
                }
                else{
                    log.error("Failed to post to Twillo API");
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
