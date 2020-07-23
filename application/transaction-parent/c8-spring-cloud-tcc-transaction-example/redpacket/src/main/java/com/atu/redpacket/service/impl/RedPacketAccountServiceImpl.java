package com.atu.redpacket.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.domain.redpacket.service.RedPacketService;
import com.atu.redpacket.service.RedPacketAccountService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Service
public class RedPacketAccountServiceImpl implements RedPacketAccountService {

    @Resource
    RedPacketService redPacketService;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketService.findByUserId(userId).getBalanceAmount();
    }
}
