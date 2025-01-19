package com.dotmonsservice.customer.service;

import com.dotmonsservice.customer.config.ConstantValues;
import com.dotmonsservice.customer.config.CustomerQueueConfig;
import com.dotmonsservice.customer.config.CustomerUriConfig;
import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.exception.BadRequestException;
import com.dotmonsservice.customer.exception.CustomerServiceException;
import com.dotmonsservice.customer.model.Customer;
import com.dotmonsservice.customer.repository.CustomerRepository;
import com.dotmonsservice.customer.dto.FraudCheckResponseDTO;
import com.dotmonsservice.customer.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
//@Scope(value = "prototype")
public class CustomerService {

    CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final CustomerQueueConfig customerConfig;
    private final CustomerUriConfig customerUriConfig;

    public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate,
                           CustomerQueueConfig customerConfig, CustomerUriConfig customerUriConfig) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.customerConfig = customerConfig;
        this.customerUriConfig = customerUriConfig;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Cacheable(value = "AllProducts")
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customer -> CustomerDTO
                .builder()
                .message(customer.getMessage())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .build());
    }


    @Transactional
    public String registerCustomer(CustomerDTO customerRegistrationRequest) {

        try {

            if ((Objects.isNull(customerRegistrationRequest.getLastName())) ||
                    (Objects.isNull(customerRegistrationRequest.getFirstName()))) {
                throw new BadRequestException("LastName or FirstName is required");
            }
            Customer customer = Customer.builder()
                    .lastName(customerRegistrationRequest.getLastName())
                    .firstName(customerRegistrationRequest.getFirstName())
                    .email(customerRegistrationRequest.getEmail())
                    .message(customerRegistrationRequest.getMessage())
                    .phoneNumber(customerRegistrationRequest.getPhoneNumber())
                    .build();

            String phoneNumber = customerRegistrationRequest.getPhoneNumber();
            String message = customerRegistrationRequest.getMessage();

            Map<String, String> request = new HashMap<>();
            request.put("phoneNumber", phoneNumber);
            request.put("message", message);

            customerRepository.saveAndFlush(customer);

            Optional<FraudCheckResponseDTO> fraudCheckResponse =
                    Optional.ofNullable(restTemplate.getForObject(customerUriConfig.getFrauduri() + "{customerId}",
                            FraudCheckResponseDTO.class, customer.getId()));


//            Optional<FraudCheckResponse> fraudCheckResponses =
//                    Optional.ofNullable(
//                            restTemplate.getForObject(
//                                    UriComponentsBuilder.fromHttpUrl(customerUriConfig.getFrauduri())
//                                            .buildAndExpand(customer.getId()) // replaces {customerId} placeholder with actual value
//                                            .toUriString(),
//                                    FraudCheckResponse.class
//                            )
//                    );

            String messageSmsQueue = "";

            if (customerConfig.getQueueType().equals(ConstantValues.KAFKA_QUEUE)) {
                // Send message to the KAFKA which sends SMS to twillo
                messageSmsQueue = restTemplate.postForObject(customerUriConfig.getKafkauri(), request, String.class);
                log.info("Message sent to kafka queue, {}", messageSmsQueue);
            } else if (customerConfig.getQueueType().equals(ConstantValues.RABBIT_QUEUE)) {
                // Send message to the RabbitMQ which sends SMS to twillo
                messageSmsQueue = restTemplate.postForObject(customerUriConfig.getRabbituri(), request, String.class);
                log.info("Message sent to rabbit queue, {}", messageSmsQueue);
            }


            if (fraudCheckResponse.isPresent() && fraudCheckResponse.get().isFradster()) {
                throw new IllegalStateException("Fraudster found");
            }

        } catch (CustomerServiceException e) {
            throw new CustomerServiceException(e.getMessage());
        }
        return "Customer registered successfully";
    }
}
