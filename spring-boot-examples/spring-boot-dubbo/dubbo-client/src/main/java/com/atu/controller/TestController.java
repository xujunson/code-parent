package com.atu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atu.service.ITestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层-入口
 */
@RestController
public class TestController {

    @Reference
    private ITestService testService;

    @GetMapping("/orderToMember")
    public String orderToMember() {

        String user = testService.getUser();

        System.out.println(user);

        return user;
    }
}
