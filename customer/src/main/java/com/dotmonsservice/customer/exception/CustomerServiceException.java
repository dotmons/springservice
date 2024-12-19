package com.dotmonsservice.customer.exception;

public class CustomerServiceException extends RuntimeException{

    public CustomerServiceException(String message) {
        super(message);
    }

    public CustomerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
