package com.atu.design.pattern.event_driven.spring_event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Tom
 * @Date: 2021/7/6 3:08 下午
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        //spring.xml 主线程执行
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");


        //全局异步
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_global_async.xml");

        //注解式配置异步 需要在 监听器的onApplicationEvent方法上 增加 @Async 注解
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_async.xml");
        PaymentService paymentService = applicationContext.getBean(PaymentService.class);

        paymentService.pay(1, "支付成功");
    }

}
