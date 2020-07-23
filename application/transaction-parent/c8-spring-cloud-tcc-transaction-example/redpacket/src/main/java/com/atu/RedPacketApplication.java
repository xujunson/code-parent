package com.atu;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-07-22 16:11
 * @description:
 */
@EnableDubbo
@SpringBootApplication
public class RedPacketApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedPacketApplication.class, args);
    }
}
