package com.dotmonsservice.customer.util;

import java.util.Optional;

public class GeneralUtil {

    public static boolean isNullOrEmpty(String s) {
        return s==null || s.trim().isEmpty();
    }
}
