package com.atu.axon.order;

import com.atu.axon.customer.command.OrderPayCommand;
import com.atu.axon.customer.event.OrderPaidEvent;
import com.atu.axon.customer.event.OrderPayFailedEvent;
import com.atu.axon.order.command.OrderFailCommand;
import com.atu.axon.order.command.OrderFinishCommand;
import com.atu.axon.order.event.OrderCreatedEvent;
import com.atu.axon.order.event.OrderFailedEvent;
import com.atu.axon.order.event.OrderFinishedEvent;
import com.atu.axon.ticket.command.OrderTicketMoveCommand;
import com.atu.axon.ticket.command.OrderTicketPreserveCommand;
import com.atu.axon.ticket.command.OrderTicketUnlockCommand;
import com.atu.axon.ticket.event.OrderTicketMovedEvent;
import com.atu.axon.ticket.event.OrderTicketPreserveFailedEvent;
import com.atu.axon.ticket.event.OrderTicketPreservedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * @author: Tom
 * @date: 2020-07-12 15:24
 * @description: 关联整个流程
 */
@Saga
public class OrderManagementSaga {
    private static final Logger LOG = LoggerFactory.getLogger(OrderManagementSaga.class);
    private String orderId;
    private String customerId;
    private String ticketId;
    private Double amount;

    //transient 保证序列化的时候不入库
    @Autowired
    private transient CommandBus commandBus;

    @Autowired
    private transient EventScheduler eventScheduler;
    private ScheduleToken timeoutToken;

    /**
     * 起始步骤
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();

        //30s之后处理fail
        timeoutToken = eventScheduler.schedule(Instant.now().plusSeconds(30), new OrderFailedEvent(orderId, "Timeout"));

        OrderTicketPreserveCommand command = new OrderTicketPreserveCommand(orderId, ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 锁票成功-支付
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreservedEvent event) {
        OrderPayCommand command = new OrderPayCommand(orderId, customerId, amount);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 锁票失败
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreserveFailedEvent event) {
        OrderFailCommand command = new OrderFailCommand(event.getOrderId(), "Preserve Failed");
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 支付成功 - 移票
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        OrderTicketMoveCommand command = new OrderTicketMoveCommand(this.ticketId, this.orderId, this.customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 支付失败 触发 unlock和 orderFail
     *
     * @param event
     */
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPayFailedEvent event) {
        OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);

        OrderFailCommand failCommand = new OrderFailCommand(event.getOrderId(), "扣费失败");
        commandBus.dispatch(asCommandMessage(failCommand), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketMovedEvent event) {
        OrderFinishCommand command = new OrderFinishCommand(orderId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    /**
     * 结束流程
     *
     * @param event
     */
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFailedEvent event) {
        LOG.info("Order:{} failed.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFinishedEvent event) {
        LOG.info("Order:{} finished.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
