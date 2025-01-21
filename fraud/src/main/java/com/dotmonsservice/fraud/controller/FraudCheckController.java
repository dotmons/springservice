package com.dotmonsservice.fraud.controller;

import com.dotmonsservice.fraud.dto.FraudCheckResponseDto;
import com.dotmonsservice.fraud.service.FraudCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")

public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    public FraudCheckController(FraudCheckService fraudCheckService){
        this.fraudCheckService = fraudCheckService;
    }
    @GetMapping(path="{customerId}")
    public FraudCheckResponseDto isFraudCheck
            (@PathVariable("customerId") Integer customerId){
        log.info("Checking Fraud CheckResponse for customer id {}", customerId);
        boolean isFradulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        return new FraudCheckResponseDto(isFradulentCustomer);
    }


}
