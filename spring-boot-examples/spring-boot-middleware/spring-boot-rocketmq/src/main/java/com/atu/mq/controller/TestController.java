package com.atu.mq.controller;

import com.atu.mq.send.ProducerSchedule;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-12-17 17:53
 * @description:
 */
@RestController
public class TestController {
    @Resource
    private ProducerSchedule producerSchedule;

    @RequestMapping(value = "/rocketTest", method = {RequestMethod.POST})
    public void rocketTest() {
        producerSchedule.send("atu-topic", "123456");
    }
}
