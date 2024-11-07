package com.dotmonsservice.kafkasms.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;


public class KafkaRecordFilterFactory implements RecordFilterStrategy<String, String> {

    private static final Logger log = LoggerFactory.getLogger(KafkaRecordFilterFactory.class);

    @Override
    public boolean filter(ConsumerRecord<String, String> consumerRecord) {
        log.info("Filtering records to capture null or empty values {} ", consumerRecord);
        // Filter out records where the value is null or empty
        String value = consumerRecord.value();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(value);
            // Accessing values
            String message = jsonObject.getString("message");
            return message == null || message.trim().isEmpty();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
