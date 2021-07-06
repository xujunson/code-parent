package com.atu.design.pattern.event_driven.observer.jdk_observer;

import java.util.Observable;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:33 下午
 * @Description:
 */
public class PaymentStatusObservable extends Observable {

    public void updatePaymentStatus(int newStatus) {
        // 业务逻辑操作
        System.out.println("更新新的支付状态为：" + newStatus);

        // 需要调用一下这个方法，表示被观察者的状态已发生变更，Observable才会通知观察者
        this.setChanged();
        // 通知观察者
        this.notifyObservers();
    }
}