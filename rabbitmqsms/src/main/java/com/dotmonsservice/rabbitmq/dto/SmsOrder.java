package com.dotmonsservice.rabbitmq.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class SmsOrder {

    private String phoneNumber;
    private String message;

}
