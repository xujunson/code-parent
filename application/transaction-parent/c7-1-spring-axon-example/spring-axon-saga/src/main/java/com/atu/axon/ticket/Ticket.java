package com.atu.axon.ticket;

import com.atu.axon.ticket.command.OrderTicketMoveCommand;
import com.atu.axon.ticket.command.OrderTicketPreserveCommand;
import com.atu.axon.ticket.command.OrderTicketUnlockCommand;
import com.atu.axon.ticket.command.TicketCreateCommand;
import com.atu.axon.ticket.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Ticket {

    private static final Logger LOG = LoggerFactory.getLogger(Ticket.class);

    @AggregateIdentifier
    private String id;

    private String name;

    private String lockUser;

    private String owner;

    public Ticket() {
    }

    @CommandHandler
    public Ticket(TicketCreateCommand command) {
        apply(new TicketCreatedEvent(command.getTicketId(), command.getName()));
    }

    /**
     * 锁票操作
     *
     * @param
     */
    @CommandHandler
    public void handle(OrderTicketPreserveCommand command) {
        //判断是否被买走
        if (this.owner != null) {
            LOG.error("Ticket is owned.");
            //已买走 锁票失败
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        } else if (this.lockUser != null && this.lockUser.equals(command.getCustomerId())) {
            //重复锁票
            LOG.info("duplicated command");
        } else if (this.lockUser == null) {
            //未被锁
            apply(new OrderTicketPreservedEvent(command.getOrderId(), command.getCustomerId(), command.getTicketId()));
        } else {
            //被别人锁票
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        }
    }

    /**
     * 解锁
     *
     * @param command
     */
    @CommandHandler
    public void handle(OrderTicketUnlockCommand command) {
        if (this.lockUser == null) {
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            LOG.error("Invalid command, ticket not locked by:{}", command.getCustomerId());
        } else {
            apply(new OrderTicketUnlockedEvent(command.getTicketId()));
        }
    }

    /**
     * 交票
     *
     * @param command
     */
    @CommandHandler
    public void handle(OrderTicketMoveCommand command) {

        if (this.lockUser == null) {
            //票未被锁
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            //票被别人锁
            LOG.error("Invalid command, ticket not locked by:{}", command.getCustomerId());
        } else {
            apply(new OrderTicketMovedEvent(command.getOrderId(), command.getTicketId(), command.getCustomerId()));
        }
    }

    /**
     * 处理创建订单
     *
     * @param event
     */
    @EventSourcingHandler
    public void onCreate(TicketCreatedEvent event) {
        this.id = event.getTicketId();
        this.name = event.getName();
        LOG.info("Executed event:{}", event);
    }

    /**
     * 处理锁票操作
     *
     * @param event
     */
    @EventSourcingHandler
    public void onPreserve(OrderTicketPreservedEvent event) {
        this.lockUser = event.getCustomerId();
        LOG.info("Executed event:{}", event);
    }

    /**
     * 解锁票
     *
     * @param event
     */
    @EventSourcingHandler
    public void onUnlock(OrderTicketUnlockedEvent event) {
        this.lockUser = null;
        LOG.info("Executed event:{}", event);
    }

    /**
     * 处理交票
     *
     * @param event
     */
    @EventSourcingHandler
    public void onMove(OrderTicketMovedEvent event) {
        this.lockUser = null;
        this.owner = event.getCustomerId();
        LOG.info("Executed event:{}", event);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLockUser() {
        return lockUser;
    }

    public String getOwner() {
        return owner;
    }
}
