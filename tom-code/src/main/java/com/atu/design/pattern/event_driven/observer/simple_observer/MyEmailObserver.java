package com.atu.design.pattern.event_driven.observer.simple_observer;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:25 下午
 * @Description:
 */
public class MyEmailObserver implements Observer{
    @Override
    public void update() {
        System.out.println("邮件服务收到通知.");
    }
}
