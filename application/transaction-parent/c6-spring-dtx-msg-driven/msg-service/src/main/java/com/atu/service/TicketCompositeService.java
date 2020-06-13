package com.atu.service;

import com.atu.dto.TicketDTO;

import java.util.List;

public interface TicketCompositeService {

    List<TicketDTO> getMyTickets(Long customerId);

}
