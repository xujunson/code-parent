package com.atu.rabbit.many;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：mark
 * @date ：Created in 2019/10/23 18:03
 * @description：
 */
@Component
@RabbitListener(queues = "atu")
public class AtuReceiver2 {
    @RabbitHandler
    public void process(String atu) {
        System.out.println("Receiver 2: " + atu);
    }
}
