package com.atu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-04-03 19:25
 * @description:
 */
@SpringBootApplication
@EnableDubbo
public class DubboConsume {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsume.class, args);
    }
}