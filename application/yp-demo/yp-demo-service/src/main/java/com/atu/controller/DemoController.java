package com.atu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name DemoController
 * @date 2020/3/18 1:31 上午
 * @describe
 */
@RestController
@Slf4j
public class DemoController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
