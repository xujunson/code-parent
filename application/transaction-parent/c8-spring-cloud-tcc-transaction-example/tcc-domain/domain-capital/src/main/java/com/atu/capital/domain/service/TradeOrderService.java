package com.atu.capital.domain.service;

import com.atu.capital.domain.model.TradeOrder;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
public interface TradeOrderService {
    public void insert(TradeOrder tradeOrder);
    public void update(TradeOrder tradeOrder);
    public TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
