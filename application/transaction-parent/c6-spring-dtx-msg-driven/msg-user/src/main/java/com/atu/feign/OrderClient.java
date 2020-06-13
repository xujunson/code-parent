package com.atu.feign;

import com.atu.dto.OrderDTO;
import com.atu.service.OrderCompositeService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends OrderCompositeService {

    @GetMapping("/{customerId}")
    List<OrderDTO> getMyOrder(@PathVariable(name = "customerId") Long id);
}
