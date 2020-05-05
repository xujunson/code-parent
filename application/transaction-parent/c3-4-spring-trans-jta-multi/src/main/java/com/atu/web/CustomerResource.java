package com.atu.web;

import com.atu.dao.CustomerRepository;
import com.atu.domain.Customer;
import com.atu.service.CustomerServiceTxInAnnotation;
import com.atu.service.CustomerServiceTxInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private CustomerServiceTxInAnnotation customerService;
    @Autowired
    private CustomerServiceTxInCode customerServiceInCode;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/code")
    public Customer createInCode(@RequestBody Customer customer) {
        return customerServiceInCode.create(customer);
    }

    @Transactional
    @PostMapping("/message/annotation")
    public void createMsgWithListener(@RequestParam String userName) {
        jmsTemplate.convertAndSend("customer:msg:new", userName);
    }

    @Transactional
    @PostMapping("/message/code")
    public void createMsgDirect(@RequestParam String userName) {
        jmsTemplate.convertAndSend("customer:msg2:new", userName);
    }

    @GetMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

}
