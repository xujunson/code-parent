package com.atu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Tom
 * @date: 2020-03-28 17:34
 * @description:
 */
@EnableFeignClients
@EnableEurekaClient
@EnableHystrix //断路器
@EnableCircuitBreaker //断路器
@EnableDiscoveryClient //开启微服务的发现能力
@EnableHystrixDashboard //监控
@SpringBootApplication
public class Search2Application {
    public static void main(String[] args) {
        SpringApplication.run(Search2Application.class, args);
    }

    @Bean
    //开启负载均衡
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
