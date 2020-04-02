package com.atu;

import com.yunphant.annotation.EnableYunphantBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author lidinglin
 * @version 1.0.0
 * @name ServiceServerApplication
 * @date 2020/4/1 1:28 上午
 * @describe
 */
@EnableYunphantBoot
@SpringBootApplication
public class ServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceServerApplication.class, args);
    }

}
