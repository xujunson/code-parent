package com.atu.design.pattern.event_driven.spring_event;

import com.oracle.jrockit.jfr.ValueDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author: Tom
 * @Date: 2021/7/6 3:08 下午
 * @Description:
 */
@Service
public class PaymentService {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void pay(int newStatus, String msg) {
        PaymentInfo paymentInfo = new PaymentInfo(newStatus, msg);
        PaymentStatusUpdateEvent paymentStatusUpdateEvent = new PaymentStatusUpdateEvent(paymentInfo);
        applicationEventPublisher.publishEvent(paymentStatusUpdateEvent);
    }
}
