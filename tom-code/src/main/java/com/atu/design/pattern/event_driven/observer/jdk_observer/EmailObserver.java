package com.atu.design.pattern.event_driven.observer.jdk_observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:32 下午
 * @Description:
 */
public class EmailObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("邮件服务收到通知");
    }
}
