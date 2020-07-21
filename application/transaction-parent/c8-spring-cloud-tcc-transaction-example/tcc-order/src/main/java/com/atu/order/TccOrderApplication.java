package com.atu.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: Tom
 * @date: 2020-07-21 19:28
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo             //启动dubbo配置与注解
@EnableAspectJAutoProxy  //启动切面类
//@ImportResource(locations = {"classpath:tcc-transaction.xml", "classpath:tcc-transaction-dubbo.xml"})
//加载框架tcc-transaction配置
public class TccOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(TccOrderApplication.class, args);
    }
}
