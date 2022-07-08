package com.atu.design.pattern.observer;

/**
 * @Author: Tom
 * @Date: 2022/7/8 17:52
 * @Description:
 */
public class ObserverDemoTest {
    public static void main(String[] args) {
        Subject subject = new ObserverableImpl();
        //添加观察者
        subject.addServer(new ObserverOneImpl());
        subject.addServer(new ObserverTwoImpl());
        //通知
        subject.notifyAllObservers("多隆");
    }
}