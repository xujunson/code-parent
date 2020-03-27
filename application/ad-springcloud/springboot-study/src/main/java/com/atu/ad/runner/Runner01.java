package com.atu.ad.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 与 ApplicationRunner 接口是在 Spring 容器启动之后做的一些自定义操作。
 * CommandLineRunner 接口被用作将其加入 Spring 容器中时执行其 run 方法。
 * 多个 CommandLineRunner 可以被同时执行在同一个 Spring 上下文中并且执行顺序是以 Order 注解的参数顺序一致。
 * 注意：需要把 CommandLineRunner 加入到 Spring 容器中。
 */
//Order可以标记执行顺序
@Order(value = 1)
@Component
public class Runner01 implements CommandLineRunner {

    /**
     * 应用程序启动之后run方法会主动运行
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Running Runner01");
    }
}
