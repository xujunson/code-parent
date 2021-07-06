package com.atu.design.pattern.event_driven.javabean_event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:47 下午
 * @Description: 事件监听器
 */
public class PaymentStatusUpdateListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("支付状态变更. eventName : %s, oldValue : %s, newValue : %s", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}