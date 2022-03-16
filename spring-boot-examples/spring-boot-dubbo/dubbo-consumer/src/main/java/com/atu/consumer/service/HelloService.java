package com.atu.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atu.provider.service.TestService;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2022/3/2 15:36
 * @Description:
 */
@Component
public class HelloService {

    @Reference(filter = {"ConsumerFilter"})
    private TestService testService;

    public String hello(){
        return testService.hello();
    }
}