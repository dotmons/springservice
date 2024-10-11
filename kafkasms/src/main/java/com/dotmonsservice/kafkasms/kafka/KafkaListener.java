package com.dotmonsservice.kafkasms.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "${kafkasms.topicbuilder}", groupId = "${kafkasms.groupid}")
    void listener(String data){
        System.out.println("Listener received: " + data);
    }
}
