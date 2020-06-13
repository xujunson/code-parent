package com.atu.service;

import com.atu.dao.CustomerRepository;
import com.atu.dao.PayInfoRepository;
import com.atu.domain.Customer;
import com.atu.domain.PayInfo;
import com.atu.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PayInfoRepository payInfoRepository;

    @Transactional
    @JmsListener(destination = "order:pay", containerFactory = "msgFactory")
    public void handle(OrderDTO msg) {
        LOG.info("Get new order to pay:{}", msg);
        // 先检查payInfo判断重复消息。
        PayInfo pay = payInfoRepository.findOneByOrderId(msg.getId());
        if (pay != null) {
            LOG.warn("Order already paid, duplicated message.");
            return;
        }

        Customer customer = customerRepository.findOne(msg.getCustomerId());
        if (customer.getDeposit() < msg.getAmount()) {
            LOG.info("No enough deposit, need amount:{}", msg.getAmount());
            msg.setStatus("NOT_ENOUGH_DEPOSIT");
            jmsTemplate.convertAndSend("order:ticket_error", msg);
            return;
        }

        pay = new PayInfo();
        pay.setOrderId(msg.getId());
        pay.setAmount(msg.getAmount());
        pay.setStatus("PAID");
        payInfoRepository.save(pay);
//        customer.setDeposit(customer.getDeposit() - msg.getAmount());
//        customerRepository.save(customer); // 如果用户下了2个订单，这个handle方法不是单线程处理，或者有多个实例，又刚好这2个请求被同时处理，
        customerRepository.charge(msg.getCustomerId(), msg.getAmount());

        msg.setStatus("PAID");
        jmsTemplate.convertAndSend("order:ticket_move", msg);
    }
}
