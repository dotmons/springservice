package com.dotmonsservice.customer.controller;

import com.dotmonsservice.customer.model.Customer;
import com.dotmonsservice.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void registerCustomer(@RequestBody Customer customerRegistrationRequest) {
        log.info("New Customer Registeration details {} " + customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }
}
