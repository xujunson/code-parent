package com.atu.order.app;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: Tom
 * @date: 2020-07-22 17:41
 * @description:
 */
@SpringBootApplication
@EnableDubbo
public class AppOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppOrderApplication.class, args);
    }
}
