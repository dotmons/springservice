package com.dotmonsservice.customer.service;

import com.dotmonsservice.customer.Customer;
import com.dotmonsservice.customer.CustomerRepository;
import com.dotmonsservice.customer.FraudCheckResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}", FraudCheckResponse.class, customer.getId());

        if (fraudCheckResponse.isFradster()) {
            throw new IllegalStateException("Fraudster found");
        }

    }
}
