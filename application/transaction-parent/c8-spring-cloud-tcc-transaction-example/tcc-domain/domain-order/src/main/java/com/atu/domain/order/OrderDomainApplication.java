package com.atu.domain.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-07-22 16:05
 * @description:
 */
@EnableDubbo
@SpringBootApplication
public class OrderDomainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderDomainApplication.class, args);
    }
}
