package com.atu.axon.ticket;

import com.atu.axon.ticket.command.TicketCreateCommand;
import com.atu.axon.ticket.query.TicketEntity;
import com.atu.axon.ticket.query.TicketEntityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @PostMapping("")
    public CompletableFuture<Object> create(@RequestParam String name) {
        LOG.info("Request to create ticket:{}", name);
        UUID ticketId = UUID.randomUUID();
        TicketCreateCommand command = new TicketCreateCommand(ticketId.toString(), name);
        return commandGateway.send(command);
    }

    @GetMapping("")
    public List<TicketEntity> all() {
        return ticketEntityRepository.findAll();
    }
}
