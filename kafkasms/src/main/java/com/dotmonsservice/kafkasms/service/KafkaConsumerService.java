package com.dotmonsservice.kafkasms.service;

import com.dotmonsservice.kafkasms.dto.TwilloResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerService {

    private RestTemplate restTemplate;

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
                        restTemplate.postForObject("http://TWILLOSMS/api/v1/smstwillo", requestHashMap, TwilloResponse.class);

                if (Objects.nonNull(twilloResponse) && (twilloResponse.success())) {
                    log.info("Successfully posted to Twillo API");
                }
                else{
                    log.error("Failed to post to Twillo API");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
