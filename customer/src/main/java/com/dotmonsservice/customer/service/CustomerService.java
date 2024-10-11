package com.dotmonsservice.customer.service;

import com.dotmonsservice.customer.model.Customer;
import com.dotmonsservice.customer.dto.CustomerRepository;
import com.dotmonsservice.customer.model.FraudCheckResponse;
import com.dotmonsservice.customer.model.TwilloResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class CustomerService {
    CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
    }

    public void registerCustomer(Customer customerRegistrationRequest) {

        Customer customer = Customer.builder()
                .lastName(customerRegistrationRequest.getLastName())
                .firstName(customerRegistrationRequest.getFirstName())
                .email(customerRegistrationRequest.getEmail()).build();

        String phoneNumber = customerRegistrationRequest.getPhoneNumber();
        String message = customerRegistrationRequest.getMessage();

        Map<String, String> request = new HashMap<>();
        request.put("phoneNumber", phoneNumber);
        request.put("message", message);

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse =
                restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
                        FraudCheckResponse.class, customer.getId());
//
//        TwilloResponse twilloResponse =
//                restTemplate.postForObject("http://TWILLOSMS/api/v1/smstwillo", request, TwilloResponse.class);

        // Send message to the queue
        String messageSmsQueue = restTemplate.postForObject("http://SMSRABBITMQ/api/v1/smspublisher", request, String.class);

        if (fraudCheckResponse.isFradster()) {
            throw new IllegalStateException("Fraudster found");
        }
        if (Objects.nonNull(messageSmsQueue)){
            log.info("Message sent to SMS queue");
        }
        else{
            throw new IllegalStateException("Message not sent");
        }
//        if (!twilloResponse.success()) {
//            log.error("Error message with status as {}", twilloResponse.message());
//            throw new IllegalStateException("Error sending SMS to user ");
//        }
//        else{
//            log.info("Success sending SMS to user with status as {} ", twilloResponse.message());
//        }

    }
}
