package com.dotmonsservice.customer.dto;

import lombok.*;

import java.util.Objects;

@Data
@Builder
public class UserDetailsDto {

    String username;
    String password;
    String role;
    String token;

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsDto that)) return false;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(role, that.role) && Objects.equals(token, that.token) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, token, status);
    }
}
