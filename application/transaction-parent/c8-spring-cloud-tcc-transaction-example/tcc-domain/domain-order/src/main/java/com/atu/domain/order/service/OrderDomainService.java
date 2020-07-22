package com.atu.domain.order.service;

import com.atu.domain.order.entity.Order;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-22 15:14
 * @description:
 */
public interface OrderDomainService {
    public Order createOrder(long payerUserId, long payeeUserId, List<Pair<Long, Integer>> productQuantities);
    public void updateOrder(Order order);
    public Order findOrderByMerchantOrderNo(String orderNo);
}

