package com.atu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZuulSeinorProducer2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulSeinorProducer2Application.class, args);
    }

}
