package com.atu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SprintBootWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprintBootWsApplication.class, args);
    }

}
