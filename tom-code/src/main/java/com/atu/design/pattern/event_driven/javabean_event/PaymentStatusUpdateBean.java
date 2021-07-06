package com.atu.design.pattern.event_driven.javabean_event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:46 下午
 * @Description: 事件源
 */
public class PaymentStatusUpdateBean {

    /**
     * 事件源 以观察模式的角度来看，则是被观察者
     */
    PropertyChangeSupport paymentlisteners = new PropertyChangeSupport(this);

    public void updateStatus(int newStatus) {
        // 模拟业务逻辑
        System.out.println("支付状态更新： " + newStatus);
        // 触发监听器处理事件
        paymentlisteners.firePropertyChange("paymentStatusUpdate", 1, newStatus);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        paymentlisteners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        paymentlisteners.removePropertyChangeListener(listener);
    }
}