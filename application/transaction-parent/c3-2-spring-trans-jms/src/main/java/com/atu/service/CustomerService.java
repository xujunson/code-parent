package com.atu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: Tom
 * @date: 2020-04-18 21:01
 * @description:
 */
@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 在customer:msg1:new队列上有 新消息时触发该方法
     *
     * @param msg
     */
    @JmsListener(destination = "customer:msg1:new")
    public void handle(String msg) {
        LOG.debug("Get msg1:{}", msg);
        String reply = "Reply1-" + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
