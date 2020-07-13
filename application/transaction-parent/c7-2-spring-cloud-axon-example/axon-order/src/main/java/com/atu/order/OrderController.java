package com.atu.order;

import com.atu.order.command.OrderCreateCommand;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;
import java.util.UUID;

/**
 * @author: Tom
 * @date: 2020-07-13 10:47
 * @description:
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("")
    public void create(@RequestBody Order order) {
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(), order.getCustomerId(),
                order.getTitle(), order.getTicketId(), order.getAmount());
        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    @PostMapping("/{orderId}")
    public Order get(@PathVariable String orderId) throws ExecutionException, InterruptedException {
        return queryGateway.query(orderId, Order.class).get();
    }
}
