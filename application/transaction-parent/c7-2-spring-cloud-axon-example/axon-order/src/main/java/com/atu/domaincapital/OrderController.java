package com.atu.domaincapital;

import com.atu.domaincapital.command.OrderCreateCommand;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

    /**
     * 新建订单
     *
     * @param order
     */
    @PostMapping("")
    public void create(@RequestBody Order order) {
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(), order.getCustomerId(),
                order.getTitle(), order.getTicketId(), order.getAmount());
        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    /**
     * 查询订单
     *
     * @param orderId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/{orderId}")
    public Order get(@PathVariable String orderId) throws ExecutionException, InterruptedException {
        return queryGateway.query(orderId, Order.class).get();
    }
}
