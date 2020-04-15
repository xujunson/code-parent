package com.atu.web;

import com.atu.dao.CustomerRepository;
import com.atu.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
