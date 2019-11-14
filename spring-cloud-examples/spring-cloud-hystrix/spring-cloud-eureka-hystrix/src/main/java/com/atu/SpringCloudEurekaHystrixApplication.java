package com.atu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaHystrixApplication.class, args);
    }

}
