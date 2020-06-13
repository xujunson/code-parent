package com.atu.feign;

import com.atu.dto.TicketDTO;
import com.atu.service.TicketCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(value = "ticket", path = "/api/ticket")
public interface TicketClient extends TicketCompositeService {

    @GetMapping("/{customerId}")
    List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId);

}
