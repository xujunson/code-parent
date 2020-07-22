package com.atu.domaincapital.event.saga;

public class OrderFailedEvent {

    private String reason;
    private String orderId;

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
