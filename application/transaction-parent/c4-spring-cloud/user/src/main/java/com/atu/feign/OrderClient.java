package com.atu.feign;

import com.atu.IOrderService;
import com.atu.dto.OrderDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 默认超时时间很短 如果业务时间过长可修改
 */
@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends IOrderService {

    @GetMapping("/{id}")
    OrderDTO getMyOrder(@PathVariable(name = "id") Long id);

    @PostMapping("")
    OrderDTO create(@RequestBody OrderDTO dto);
}
