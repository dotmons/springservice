package com.dotmonsservice.fraud.service;

import com.dotmonsservice.fraud.model.FraudCheckHistory;
import com.dotmonsservice.fraud.repository.FraudCheckHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
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

       try {
           fraudCheckHistoryRepository.save(fraudCheckHistory);
       }
       catch (Exception e) {
           return false;
       }
        return false;
    }
}
