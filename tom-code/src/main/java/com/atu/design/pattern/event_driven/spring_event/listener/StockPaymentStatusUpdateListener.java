package com.atu.design.pattern.event_driven.spring_event.listener;

import com.atu.design.pattern.event_driven.spring_event.PaymentStatusUpdateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2021/7/6 3:01 下午
 * @Description: 无序的事件监听器：库存服务监听器
 */
@Component
public class StockPaymentStatusUpdateListener implements ApplicationListener<PaymentStatusUpdateEvent> {

    @Override
    public void onApplicationEvent(PaymentStatusUpdateEvent event) {
        System.out.println("库存服务, 收到支付状态更新的事件. " + event + " - Thread: " + Thread.currentThread().getName());
    }
}