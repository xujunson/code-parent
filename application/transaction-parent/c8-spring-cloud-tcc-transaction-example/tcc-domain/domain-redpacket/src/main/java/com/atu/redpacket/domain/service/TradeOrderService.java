package com.atu.redpacket.domain.service;

import com.atu.redpacket.domain.model.TradeOrder;

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
