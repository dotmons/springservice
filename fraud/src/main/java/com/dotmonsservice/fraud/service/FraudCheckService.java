package com.dotmonsservice.fraud.service;

import com.dotmonsservice.fraud.FraudCheckHistory;
import com.dotmonsservice.fraud.FraudCheckHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
    }


    public boolean isFraudulentCustomer(Integer customerId) {

       FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdDateTime(LocalDateTime.now())
                .build();

        fraudCheckHistoryRepository.save(fraudCheckHistory);
        return false;
    }
}
