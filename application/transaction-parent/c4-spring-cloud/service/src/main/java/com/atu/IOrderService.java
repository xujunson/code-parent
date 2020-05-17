package com.atu;

import com.atu.dto.OrderDTO;

public interface IOrderService {

    OrderDTO create(OrderDTO dto);

    OrderDTO getMyOrder(Long id);
}
