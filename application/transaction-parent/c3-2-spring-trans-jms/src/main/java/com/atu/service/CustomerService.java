package com.atu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 在customer:msg1:new队列上有 新消息时触发该方法
     *
     * @param msg
     */
    @Transactional
    @JmsListener(destination = "customer:msg1:new", containerFactory = "msgFactory")
    public void handle(String msg) {
        LOG.debug("Get msg1:{}", msg);
        String reply = "Reply1-" + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }

    /**
     * 代码方式实现
     *
     * @param msg
     */
    @JmsListener(destination = "customer:msg2:new", containerFactory = "msgFactory")
    public void handleInCode(String msg) {
        LOG.debug("Get msg2:{}", msg);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String reply = "Reply1-2 - " + msg;
            jmsTemplate.convertAndSend("customer:msg:reply", reply);
            if (!msg.contains("error")) {
                transactionManager.commit(status);
            } else {
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }


    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
