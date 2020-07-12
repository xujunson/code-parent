package com.atu.axon.ticket.query;

import com.atu.axon.ticket.event.OrderTicketMovedEvent;
import com.atu.axon.ticket.event.OrderTicketPreservedEvent;
import com.atu.axon.ticket.event.OrderTicketUnlockedEvent;
import com.atu.axon.ticket.event.TicketCreatedEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 映射
 */
@Component
public class TicketProjector {

    private static final Logger LOG = LoggerFactory.getLogger(TicketProjector.class);

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @EventSourcingHandler
    public void onCreate(TicketCreatedEvent event) {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(event.getTicketId());
        ticket.setName(event.getName());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onPreserve(OrderTicketPreservedEvent event) {
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setLockUser(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onUnlock(OrderTicketUnlockedEvent event) {
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

    @EventSourcingHandler
    public void onMove(OrderTicketMovedEvent event) {
        TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
        ticket.setLockUser(null);
        ticket.setOwner(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed event in TicketProjector:{}", event);
    }

}
