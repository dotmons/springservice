package com.dotmonsservice.customer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String message;
}
