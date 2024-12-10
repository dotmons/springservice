package com.dotmonsservice.customer.controller;

import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.model.Customer;
import com.dotmonsservice.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerRegistrationRequest) {
        log.info("New Customer Registration details {} ", customerRegistrationRequest);
        try {
            customerService.registerCustomer(customerRegistrationRequest);
            return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public Page<Customer> getAllCustomers(@PageableDefault(size = 3, sort = "lastName") Pageable pageable) {

            return customerService.getCustomers(pageable);

//        try{
//            List<Customer> customerList = customerService.getCustomers();
//            if (customerList.isEmpty()) {
//                log.info("No customers found");
//                return ResponseEntity.noContent().build();
//            }
//            log.info("Found {} customers", customerList.size());
//            return ResponseEntity.ok(customerList);
//        }
//        catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.status(500).body(Collections.emptyList());
//        }

    }
}
