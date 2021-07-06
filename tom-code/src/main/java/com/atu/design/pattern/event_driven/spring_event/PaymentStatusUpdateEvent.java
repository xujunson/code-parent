package com.atu.design.pattern.event_driven.spring_event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: Tom
 * @Date: 2021/7/6 3:00 下午
 * @Description: 支付状态更新事件
 */
public class PaymentStatusUpdateEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PaymentStatusUpdateEvent(PaymentInfo source) {
        super(source);
    }
}
