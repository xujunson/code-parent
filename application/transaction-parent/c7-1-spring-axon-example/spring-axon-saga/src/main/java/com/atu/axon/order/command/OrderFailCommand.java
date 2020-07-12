package com.atu.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author: Tom
 * @date: 2020-07-12 10:37
 * @description:
 */
public class OrderFailCommand {
    @TargetAggregateIdentifier
    private String orderId;

    private String reason;

    public OrderFailCommand(String orderId, String reason) {
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
