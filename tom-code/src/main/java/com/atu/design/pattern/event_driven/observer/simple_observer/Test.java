package com.atu.design.pattern.event_driven.observer.simple_observer;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:27 下午
 * @Description: 自定义观察者设计模式，当支付状态更新时，通知邮件服务和库存服务
 */
public class Test {
    public static void main(String[] args) {

        // "支付状态更新"->看做一个事件，可以被监听的事件

        // 被观察者。即事件源
        MyPaymentStatusUpdateSubject myPaymentStatusUpdateSubject = new MyPaymentStatusUpdateSubject();

        // 观察者。即事件监听器
        MyEmailObserver myEmailObserver = new MyEmailObserver();
        MyStockObserver myStockObserver = new MyStockObserver();

        // 添加观察者。
        myPaymentStatusUpdateSubject.addObserver(myEmailObserver);
        myPaymentStatusUpdateSubject.addObserver(myStockObserver);

        // 发布事件。支付状态更新。
        myPaymentStatusUpdateSubject.updatePaymentStatus(2);
    }
}