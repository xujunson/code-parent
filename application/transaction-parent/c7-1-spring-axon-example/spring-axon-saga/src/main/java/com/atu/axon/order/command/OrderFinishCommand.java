package com.atu.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author: Tom
 * @date: 2020-07-12 10:37
 * @description:
 */
public class OrderFinishCommand {
    @TargetAggregateIdentifier
    private String orderId;
    public OrderFinishCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
