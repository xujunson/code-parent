package com.atu.controller;

import com.atu.service.TestService;
import com.atu.service.TestService2;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Tom
 * @date: 2020-04-03 19:31
 * @description:
 */
@RestController
public class TestDubboController {
    @Reference(version = "1.0.0")
    TestService2 service2;
    @Reference(version = "1.0.0")
    TestService service;

    //http://127.0.0.1:7000/test1
    @GetMapping("test1")
    public String test1() {
        return service.showName();
    }

    //http://127.0.0.1:7000/test2
    @GetMapping("test2")
    public String test2() {
        return service2.showName();
    }
}

