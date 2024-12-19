package com.dotmonsservice.customer.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("customeruri")
@Data
public class CustomerUriConfig {

    private String kafkauri;
    private String rabbituri;
    private String frauduri;
}
