package com.atu.rabbit.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author ：mark
 * @date ：Created in 2019/10/23 14:20
 * @description： 发送者
 */
@Configuration
public class HelloSender {
    //springboot版本为2.2.0此处会报错
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("sender: " + context);
        rabbitTemplate.convertAndSend("hello" + context);
    }

}
