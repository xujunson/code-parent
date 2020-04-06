package com.atu.service.impl;

import com.atu.service.TestService2;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author: Tom
 * @date: 2020-04-03 19:26
 * @description:
 */

@Service(version = "1.0.0")
public class TestService2Impl implements TestService2 {
    @Override
    public String showName() {
        System.out.println("TestService2 -------------------");
        return "HELLO   TestService2";
    }
}