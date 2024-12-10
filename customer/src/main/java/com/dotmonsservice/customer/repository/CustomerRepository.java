package com.dotmonsservice.customer.repository;

import com.dotmonsservice.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


}