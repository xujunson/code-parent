package com.atu.ad.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner 阅读官方的 javadoc，可以发现，与 CommandLineRunner 几乎一样，区别在于接收的参数不一样。
 * CommandLineRunner 的参数是最原始的参数，没有做任何处理。ApplicationRunner 的参数是 ApplicationArguments，是对原始参数做了进一步的封装。
 */

/**
 * 默认情况下 ApplicationRunner会优先于CommandLineRunner执行
 */
@Order(value = 2)
@Component
public class Runner02 implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        System.out.println("Running Runner02");
    }
}
