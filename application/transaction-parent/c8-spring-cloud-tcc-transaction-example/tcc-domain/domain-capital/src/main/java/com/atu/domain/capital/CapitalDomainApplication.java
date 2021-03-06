package com.atu.domain.capital;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-07-22 14:28
 * @description:
 */
@EnableDubbo
@SpringBootApplication
public class CapitalDomainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CapitalDomainApplication.class, args);
    }
}
