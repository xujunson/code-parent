package com.atu;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Tom
 * @date: 2020-04-03 16:56
 * @description:
 */

@SpringBootApplication
@EnableDubbo // 开启Dubbo
public class DubboService {
    public static void main(String[] args) {
        SpringApplication.run(DubboService.class, args);
    }
}
