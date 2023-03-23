package com.atu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ：mark
 * @date ：Created in 2019/11/12 10:28
 * @description：${description}
 */

@Service
public class TestAsyncService {
    Logger log = LoggerFactory.getLogger(TestAsyncService.class);

    //注： @Async所修饰的函数不要定义为static类型，这样异步调用不会生效
    // 发送提醒短信 1
    @Async("taskExecutor")
    public void service1() throws InterruptedException {
        System.out.println("--------start-service1------------");
        Thread.sleep(5000); // 模拟耗时
        System.out.println("--------end-service1------------");
    }

    // 发送提醒短信 2
    @Async("taskExecutor")
    public void service2() throws InterruptedException {

        System.out.println("--------start-service2------------");
        Thread.sleep(2000); // 模拟耗时
        System.out.println("--------end-service2------------");

    }
}
