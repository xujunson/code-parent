package com.atu;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-04-03 16:54
 * @description: 启动入口
 */
@SpringBootApplication
@EnableDubbo
public class DubboClient {

    public static void main(String[] args) {
        SpringApplication.run(DubboClient.class, args);
    }
}
