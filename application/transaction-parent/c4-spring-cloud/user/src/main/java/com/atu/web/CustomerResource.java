package com.atu.web;

import com.atu.dao.CustomerRepository;
import com.atu.domain.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @PostConstruct
    public void init() {
        Customer customer = new Customer();
        customer.setUsername("imooc");
        customer.setPassword("111111");
        customer.setRole("User");
        customerRepository.save(customer);
    }

    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

   /* @GetMapping("/my")
    @HystrixCommand
    public Map getMyInfo() {
        Customer customer = customerRepository.findOneByUsername("imooc");
        OrderDTO order = orderClient.getMyOrder(1l);
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("order", order);
        return result;
    }*/

}
