package com.atu.web;

import com.atu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Tom
 * @date: 2020-04-18 21:01
 * @description:
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CustomerService customerService;

    /**
     * 通过监听器触发，有两个消息的读写
     *
     * @param msg
     */
    @PostMapping("/message1/listen")
    public void create(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg1:new", msg);
    }

    /**
     * 直接触发，有一个消息的读写
     *
     * @param msg
     */
    @PostMapping("/message1/direct")
    public void handle(@RequestParam String msg) {
        customerService.handle(msg);
    }

    /**
     * 读取消息
     *
     * @return
     */
    @GetMapping("/message")
    public String getMsg() {
        //超时时间 2s
        jmsTemplate.setReceiveTimeout(2000);
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }

}
