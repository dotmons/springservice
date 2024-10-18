package com.dotmonsservice.kafkasms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Component
public class Smsbody {

    private String message;
    private String phoneNumber;
}
