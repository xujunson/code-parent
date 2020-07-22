package com.atu.redpacket.service;

import com.atu.redpacket.service.dto.CapitalTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @author: Tom
 * @date: 2020-07-21 17:26
 * @description:
 */
public interface CapitalTradeOrderService {
    @Compensable
    public String record(CapitalTradeOrderDto tradeOrderDto);
}
