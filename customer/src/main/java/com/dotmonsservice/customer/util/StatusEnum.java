package com.dotmonsservice.customer.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    REGISTERED("registered"),
    FAILURE("failure");

    private final String status;
}
