package com.atu;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Tom
 * @Date: 2021/5/31 3:11 下午
 * @Description:
 */

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApp.class, args);
    }
}
