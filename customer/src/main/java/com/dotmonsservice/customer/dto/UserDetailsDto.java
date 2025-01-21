package com.dotmonsservice.customer.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {

    String username;
    String password;
    String role;
    String token;
    String status;
}
