package com.atu.axon.order.event;

/**
 * @author: Tom
 * @date: 2020-07-12 10:38
 * @description:
 */
public class OrderFailedEvent {
    private String reason;
    private String orderId;

    public OrderFailedEvent() {
    }

    public OrderFailedEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}
