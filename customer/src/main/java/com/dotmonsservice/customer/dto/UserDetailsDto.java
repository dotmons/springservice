package com.dotmonsservice.customer.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// To exclude null from the json output
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDto {

    private String username;

    // To ignore this from being processed over the network
    @JsonIgnore
    private String password;
    private String role;
    private String token;
    private String status;
}
