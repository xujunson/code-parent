package com.atu.order.infrastructure.dao;

import com.atu.order.entity.OrderLine;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
public interface OrderLineDao {
    void insert(OrderLine orderLine);
}
