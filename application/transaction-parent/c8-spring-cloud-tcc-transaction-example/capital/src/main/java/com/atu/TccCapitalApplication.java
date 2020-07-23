package com.atu;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-07-22 11:07
 * @description:
 */
@EnableDubbo
@SpringBootApplication
public class TccCapitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(TccCapitalApplication.class, args);
    }

}
