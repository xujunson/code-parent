package com.atu.redpacket.service;

import com.atu.redpacket.api.service.RedPacketAccountService;
import com.atu.redpacket.domain.service.RedPacketService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@DubboService
public class RedPacketAccountServiceImpl implements RedPacketAccountService {

    @Autowired
    RedPacketService redPacketService;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketService.findByUserId(userId).getBalanceAmount();
    }
}
