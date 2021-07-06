package com.atu.design.pattern.event_driven.spring_event.listener;

import com.atu.design.pattern.event_driven.spring_event.PaymentInfo;
import com.atu.design.pattern.event_driven.spring_event.PaymentStatusUpdateEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2021/7/6 3:02 下午
 * @Description: 有序监听器：邮件服务监听器
 */
@Component
public class MailPaymentStatusUpdateListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == PaymentStatusUpdateEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == PaymentInfo.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("邮件服务, 收到支付状态更新的通知. " + event + " - Thread: " + Thread.currentThread().getName());
    }

    /**
     * 排序 数字越小执行的优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
