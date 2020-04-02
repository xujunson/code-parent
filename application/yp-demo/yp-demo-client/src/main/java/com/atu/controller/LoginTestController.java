package com.atu.controller;

import com.atu.service.LoginTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name LoginTestController
 * @date 2020/3/18 1:31 上午
 * @describe
 */
@RestController
@Slf4j
public class LoginTestController {

    @Resource
    LoginTestService loginTestService;

    @PostMapping("/")
    public String test(@RequestParam String userName, @RequestParam String passWord) {
        return loginTestService.login(userName, passWord);
    }
}
