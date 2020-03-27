package com.atu.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Tom
 * @date: 2020-03-27 11:36
 * @description:
 */

//EnableFeignClients：可以在本微服务调用其他微服务
@EnableFeignClients
//EnableCircuitBreaker：断路器，实现监控
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SponsorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }
}
