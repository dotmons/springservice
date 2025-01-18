package com.dotmonsservice.customer.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRoleEnum {

    ADMIN("1"),
    USER("2");

    private String userType;

    public String getUserType() {
        return userType;
    }

    public static UserRoleEnum fromUserType(String userType) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role.getUserType().equals(userType)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant for userType: " + userType);
    }
}