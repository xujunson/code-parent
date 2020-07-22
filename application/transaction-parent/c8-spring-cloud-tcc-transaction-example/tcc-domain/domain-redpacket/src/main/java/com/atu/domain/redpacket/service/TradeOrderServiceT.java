package com.atu.domain.redpacket.service;

import com.atu.domain.redpacket.model.TradeOrder;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
public interface TradeOrderServiceT {
    public void insert(TradeOrder tradeOrder);
    public void update(TradeOrder tradeOrder);
    public TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
