package com.atu.web;

import com.atu.dao.TicketRepository;
import com.atu.domain.Ticket;
import com.atu.dto.OrderDTO;
import com.atu.dto.TicketDTO;
import com.atu.service.TicketCompositeService;
import com.atu.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ticket")
public class TicketResource implements TicketCompositeService {

    /**
     * 初始化买票数据
     */
    @PostConstruct
    public void init() {
        if (ticketRepository.count() > 0) {
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setName("No.1");
        ticket.setTicketNum(100L);
        ticketRepository.save(ticket);
    }

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @PostMapping("")
    public OrderDTO create(@RequestBody OrderDTO dto) {
        return dto;
    }

    @GetMapping("/{customerId}")
    public List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId) {
        List<Ticket> tickets = ticketRepository.findAllByOwner(customerId);
        return tickets.stream().map(ticket -> {
            TicketDTO dto = new TicketDTO();
            dto.setTicketNum(ticket.getTicketNum());
            dto.setId(ticket.getId());
            dto.setLockUser(ticket.getLockUser());
            dto.setName(ticket.getName());
            dto.setOwner(ticket.getOwner());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 锁票
     * 方式1
     * @param dto
     * @return
     */
    @PostMapping("/lock")
    public Ticket lock(@RequestBody OrderDTO dto) {
        return ticketService.lockTicket(dto);
    }

    /**
     * 锁票
     * 方式2
     * @param dto
     * @return
     */
    @PostMapping("/lock2")
    public int lock2(@RequestBody OrderDTO dto) {
        return ticketService.lockTicket2(dto);
    }

    @GetMapping("")
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

}
