package com.atu.redpacket.domain;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-07-22 14:28
 * @description:
 */
@EnableDubbo
@SpringBootApplication
public class RedpacketDomainApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedpacketDomainApplication.class, args);
    }
}
