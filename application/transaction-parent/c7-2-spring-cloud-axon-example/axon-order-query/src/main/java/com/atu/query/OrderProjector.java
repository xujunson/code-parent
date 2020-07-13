package com.atu.query;

import com.atu.order.event.saga.OrderFailedEvent;
import com.atu.order.event.saga.OrderFinishedEvent;
import com.atu.order.event.saga.OrderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("OrderEventProcessor")
public class OrderProjector {

    @Autowired
    private OrderEntityRepository repository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();
        order.setId(event.getOrderId());
        order.setCustomerId(event.getCustomerId());
        order.setTicketId(event.getTicketId());
        order.setAmount(event.getAmount());
        order.setCreatedDate(event.getCreatedDate());
        order.setTitle(event.getTitle());
        order.setStatus("NEW");
        repository.save(order);
    }

//    @EventSourcingHandler
//    public void onPaid(OrderPaidEvent event) {
//        OrderEntity order = repository.findOne(event.getOrderId());
//        order.setStatus("PAID");
//        repository.save(order);
//    }

    @EventSourcingHandler
    public void onFinished(OrderFinishedEvent event) {
        OrderEntity order = repository.findOne(event.getOrderId());
        order.setStatus("FINISH");
        repository.save(order);
    }

    @EventSourcingHandler
    public void onFailed(OrderFailedEvent event) {
        OrderEntity order = repository.findOne(event.getOrderId());
        order.setStatus("FAILED");
        order.setReason(event.getReason());
        repository.save(order);
    }
}
