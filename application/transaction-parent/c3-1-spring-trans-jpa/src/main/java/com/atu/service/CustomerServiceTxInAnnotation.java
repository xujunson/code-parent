package com.atu.service;

import com.atu.dao.CustomerRepository;
import com.atu.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transactional标签方式实现
 */
@Service
public class CustomerServiceTxInAnnotation {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceTxInAnnotation.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer) {
        LOG.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }
}
