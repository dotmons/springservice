package com.dotmonsservice.customer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerServiceException.class)
    public ResponseEntity<String> handleCustomerServiceException(CustomerServiceException ex) {
        log.error("Customer Service Exception", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unkwown Error occurred ");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest webRequest) {
        log.error("Error: {} request: {} ", ex.getMessage(), webRequest);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        log.error("Bad Request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error with request. Error occurred, Name or Surname is missing");
    }
}
