package com.atu;

import com.yunphant.annotation.EnableYunphantBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name ClientServiceApplication
 * @date 2020/3/18 1:31 上午
 * @describe
 */
@EnableYunphantBoot
@SpringBootApplication
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

}
