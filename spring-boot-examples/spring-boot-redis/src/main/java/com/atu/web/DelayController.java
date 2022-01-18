package com.atu.web;

import com.atu.delaydemo.RedisDelayDemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Tom
 * @Date: 2021/10/24 11:59
 * @Description:
 */
@RestController
public class DelayController {
    @Resource
    private RedisDelayDemo delayDemo;

    @RequestMapping("/delayTest")
    public void delayTest() {
        delayDemo.setDelayTasks(5000L);
        delayDemo.listenDelayLoop();
    }
}
