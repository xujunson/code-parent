package com.atu.axon.order.event;

import java.time.ZonedDateTime;

/**
 * @author: Tom
 * @date: 2020-07-12 10:38
 * @description:
 */
public class OrderCreatedEvent {
    private String orderId;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    private ZonedDateTime createdDate;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String customerId, String title, String ticketId, Double amount, ZonedDateTime createdDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.title = title;
        this.ticketId = ticketId;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }
}
