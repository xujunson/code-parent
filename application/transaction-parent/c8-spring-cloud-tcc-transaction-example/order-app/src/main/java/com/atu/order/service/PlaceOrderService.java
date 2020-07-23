package com.atu.order.service;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-22 16:36
 * @description:
 */
public interface PlaceOrderService {
    public String placeOrder(long payerUserId, long shopId, List<Pair<Long, Integer>> productQuantities, final BigDecimal redPacketPayAmount);

}
