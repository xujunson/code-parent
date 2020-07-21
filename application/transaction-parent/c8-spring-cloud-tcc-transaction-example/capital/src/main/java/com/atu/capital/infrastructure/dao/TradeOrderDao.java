package com.atu.capital.infrastructure.dao;

import com.atu.capital.entity.TradeOrder;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
public interface TradeOrderDao {

    int insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
