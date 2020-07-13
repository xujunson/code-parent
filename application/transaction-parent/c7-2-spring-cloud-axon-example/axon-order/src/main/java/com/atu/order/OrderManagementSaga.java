package com.atu.order;

/**
 * @author: Tom
 * @date: 2020-07-13 10:47
 * @description:
 */

import com.atu.order.command.OrderFailCommand;
import com.atu.order.command.OrderFinishCommand;
import com.atu.order.event.saga.OrderFailedEvent;
import com.atu.order.event.saga.OrderFinishedEvent;
import com.atu.order.event.saga.OrderCreatedEvent;
import com.atu.ticket.command.OrderTicketMoveCommand;
import com.atu.ticket.command.OrderTicketPreserveCommand;
import com.atu.ticket.command.OrderTicketUnlockCommand;
import com.atu.ticket.event.saga.OrderTicketMovedEvent;
import com.atu.ticket.event.saga.OrderTicketPreserveFailedEvent;
import com.atu.ticket.event.saga.OrderTicketPreservedEvent;
import com.atu.user.command.OrderPayCommand;
import com.atu.user.event.saga.OrderPaidEvent;
import com.atu.user.event.saga.OrderPayFailedEvent;
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
 * @date: 2020-07-13 10:47
 * @description: 关联整个流程
 */
@Saga
public class OrderManagementSaga {

    private static final Logger LOG = LoggerFactory.getLogger(OrderManagementSaga.class);

    @Autowired
    private transient CommandBus commandBus;
    //处理超时
    @Autowired
    private transient EventScheduler eventScheduler;

    private String orderId;
    private String customerId;
    private String ticketId;
    private Double amount;

    private ScheduleToken timeoutToken;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();

        timeoutToken = eventScheduler.schedule(Instant.now().plusSeconds(30), new OrderPayFailedEvent(this.orderId));

        OrderTicketPreserveCommand command = new OrderTicketPreserveCommand(orderId, ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreservedEvent event) {
        OrderPayCommand command = new OrderPayCommand(orderId, customerId, amount);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderTicketPreserveFailedEvent event) {
        OrderFailCommand command = new OrderFailCommand(event.getOrderId(), "Preserve Failed");
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        OrderTicketMoveCommand command = new OrderTicketMoveCommand(this.ticketId, this.orderId, this.customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPayFailedEvent event) {
        OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(ticketId, customerId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);

        OrderFailCommand failCommand = new OrderFailCommand(event.getOrderId(), "Pay Failed");
        commandBus.dispatch(asCommandMessage(failCommand), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketMovedEvent event) {
        OrderFinishCommand command = new OrderFinishCommand(orderId);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderFinishedEvent event) {
        LOG.info("Order:{} finished.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderFailedEvent event) {
        LOG.info("Order:{} failed.", event.getOrderId());
        if (this.timeoutToken != null) {
            eventScheduler.cancelSchedule(this.timeoutToken);
        }
    }
}
