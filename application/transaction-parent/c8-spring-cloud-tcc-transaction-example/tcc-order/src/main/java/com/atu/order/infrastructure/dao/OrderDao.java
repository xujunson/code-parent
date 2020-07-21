package com.atu.order.infrastructure.dao;

import com.atu.order.entity.Order;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
public interface OrderDao {

    public int insert(Order order);

    public int update(Order order);

    Order findByMerchantOrderNo(String merchantOrderNo);
}
