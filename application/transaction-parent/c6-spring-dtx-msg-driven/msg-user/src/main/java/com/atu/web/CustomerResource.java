package com.atu.web;

import com.atu.dao.CustomerRepository;
import com.atu.domain.Customer;
import com.atu.dto.OrderDTO;
import com.atu.dto.TicketDTO;
import com.atu.service.OrderCompositeService;
import com.atu.service.TicketCompositeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @PostConstruct
    public void init() {
        if (customerRepository.count() > 0) {
            return;
        }
        Customer customer = new Customer();
        customer.setUsername("atu");
        customer.setPassword("111111");
        customer.setRole("User");
        customer.setDeposit(1000);
        customerRepository.save(customer);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderCompositeService orderClient;
    @Autowired
    private TicketCompositeService ticketClient;

    @PostMapping("")
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("")
    @HystrixCommand
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("/my")
    @HystrixCommand
    public Map getMyInfo() {
        Customer customer = customerRepository.findOneByUsername("atu");
        List<OrderDTO> orders = orderClient.getMyOrder(customer.getId());
        List<TicketDTO> tickets = ticketClient.getMyTickets(customer.getId());
        Map result = new HashMap();
        result.put("customer", customer);
        result.put("orders", orders);
        result.put("tickets", tickets);
        return result;
    }

}
