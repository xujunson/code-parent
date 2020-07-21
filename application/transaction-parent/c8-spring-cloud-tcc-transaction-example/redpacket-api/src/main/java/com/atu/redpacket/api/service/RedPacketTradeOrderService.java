package com.atu.redpacket.api.service;

import com.atu.redpacket.api.service.dto.RedPacketTradeOrderDto;
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
