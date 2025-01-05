package com.dotmonsservice.customer.controller;

import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.exception.CustomerServiceException;
import com.dotmonsservice.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody @Validated CustomerDTO customerRegistrationRequest) {
        log.info("New Customer Registration details {} ", customerRegistrationRequest);
        return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationRequest));
    }

    @GetMapping()
    public Page<CustomerDTO> getAllCustomers(@PageableDefault(size = 10, sort = "lastName") Pageable pageable) {

        return customerService.getAllCustomers(pageable);
    }
}
