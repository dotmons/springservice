package com.dotmonsservice.kafkasms.config;

import com.dotmonsservice.kafkasms.util.KafkaRecordFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.util.backoff.FixedBackOff;


import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> consumerConfigs() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());

        // Use this or uncomment the line below to filter out records not matching the requirements below
        // This is not sent to the listener class
        factory.setRecordFilterStrategy(new KafkaRecordFilterFactory());

//        // Set the record filter strategy
//        factory.setRecordFilterStrategy(new RecordFilterStrategy<String, String>() {
//            @Override
//            public boolean filter(ConsumerRecord<String, String> consumerRecord) {
//                log.info("Filtering records to capture null or empty values {} ", consumerRecord);
//                // Filter out records where the value is null or empty
//                String value = consumerRecord.value();
//
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(value);
//                    // Accessing values
//                    String message = jsonObject.getString("message");
//                    return message == null || message.trim().isEmpty();
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        return factory;
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {

        FixedBackOff fixedBackOff = new FixedBackOff(10000L, 10L);

        return new DefaultErrorHandler((record, exception) -> {
            log.error("Error processing record with key {} and value {} at offset {} in partition {}. Skipping this record.",
                    record.key(), record.value(), record.offset(), record.partition(), exception);
        }, fixedBackOff);
    }

}
