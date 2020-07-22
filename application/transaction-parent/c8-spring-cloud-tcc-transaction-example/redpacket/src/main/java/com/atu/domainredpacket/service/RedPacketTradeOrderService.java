package com.atu.domainredpacket.service;

import com.atu.domainredpacket.service.dto.RedPacketTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @author: Tom
 * @date: 2020-07-21 19:04
 * @description:
 */
public interface RedPacketTradeOrderService {

    @Compensable
    public String record(RedPacketTradeOrderDto tradeOrderDto);
}
