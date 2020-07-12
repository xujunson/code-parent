package com.atu.axon.ticket.event;

/**
 * 锁票失败
 */
public class OrderTicketPreserveFailedEvent {

    private String orderId;

    public OrderTicketPreserveFailedEvent() {
    }

    public OrderTicketPreserveFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
