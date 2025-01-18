package com.dotmonsservice.customer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsDto {

    String username;
    String password;
    String role;
    String token;
    String status;
}
