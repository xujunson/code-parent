package com.atu.rabbit.many;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：mark
 * @date ：Created in 2019/10/23 18:03
 * @description：
 */
@Component
public class AtuSender1 {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(int i) {
        String context = "spirng boot atu queue" + " ****** " + i;
        System.out.println("Sender1 : " + context);
        this.amqpTemplate.convertAndSend("atu", context);
    }
}
