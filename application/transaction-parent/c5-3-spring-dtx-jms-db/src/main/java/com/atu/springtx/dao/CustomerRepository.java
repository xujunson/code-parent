package com.atu.springtx.dao;

import com.atu.springtx.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
