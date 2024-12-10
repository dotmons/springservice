package com.dotmonsservice.customer.service;

import com.dotmonsservice.customer.config.ConstantValues;
import com.dotmonsservice.customer.config.CustomerConfig;
import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.model.Customer;
import com.dotmonsservice.customer.repository.CustomerRepository;
import com.dotmonsservice.customer.model.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerService {
    CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final CustomerConfig customerConfig;

    public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate, CustomerConfig customerConfig) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.customerConfig = customerConfig;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


    public String registerCustomer(CustomerDTO customerRegistrationRequest) {

        try {
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

            FraudCheckResponse fraudCheckResponse =
                    restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
                            FraudCheckResponse.class, customer.getId());

            // To send SMS directly to twillo REST API
            //TwilloResponse twilloResponse = restTemplate.postForObject("http://TWILLOSMS/api/v1/smstwillo", request, TwilloResponse.class);

            String messageSmsQueue = "";

            if (customerConfig.getQueueType().equals(ConstantValues.KAFKA_QUEUE)) {
                // Send message to the KAFKA which sends SMS to twillo
                messageSmsQueue = restTemplate.postForObject("http://KAFKASMS/api/v1/sendsmstokafka", request, String.class);
                log.info("Message sent to kafka queue, {}", messageSmsQueue);
            } else if (customerConfig.getQueueType().equals(ConstantValues.RABBIT_QUEUE)) {
                // Send message to the RabbitMQ which sends SMS to twillo
                messageSmsQueue = restTemplate.postForObject("http://SMSRABBITMQ/api/v1/smspublisher", request, String.class);
                log.info("Message sent to rabbit queue, {}", messageSmsQueue);
            }


            if (fraudCheckResponse.isFradster()) {
                throw new IllegalStateException("Fraudster found");
            }


//        if (!twilloResponse.success()) {
//            log.error("Error message with status as {}", twilloResponse.message());
//            throw new IllegalStateException("Error sending SMS to user ");
//        }
//        else{
//            log.info("Success sending SMS to user with status as {} ", twilloResponse.message());
//        }

        }
        catch (IllegalStateException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        return "Customer registered successfully";
    }
}
