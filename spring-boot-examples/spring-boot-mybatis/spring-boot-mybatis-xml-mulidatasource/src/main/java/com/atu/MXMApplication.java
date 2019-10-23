package com.atu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;

@SpringBootApplication
public class MXMApplication {

    public static void main(String[] args) {
        SpringApplication.run(MXMApplication.class, args);
    }

}
