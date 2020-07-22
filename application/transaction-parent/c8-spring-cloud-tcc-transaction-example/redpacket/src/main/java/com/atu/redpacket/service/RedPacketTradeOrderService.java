package com.atu.redpacket.service;

import com.atu.redpacket.service.dto.RedPacketTradeOrderDto;
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
