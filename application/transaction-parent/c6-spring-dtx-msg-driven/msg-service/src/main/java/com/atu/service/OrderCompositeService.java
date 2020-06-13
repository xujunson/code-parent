package com.atu.service;

import com.atu.dto.OrderDTO;

import java.util.List;

public interface OrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}
