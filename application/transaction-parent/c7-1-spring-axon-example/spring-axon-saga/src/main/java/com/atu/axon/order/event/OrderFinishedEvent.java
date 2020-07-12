package com.atu.axon.order.event;

/**
 * @author: Tom
 * @date: 2020-07-12 10:39
 * @description:
 */
public class OrderFinishedEvent {
    private String orderId;

    public OrderFinishedEvent() {
    }

    public OrderFinishedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
