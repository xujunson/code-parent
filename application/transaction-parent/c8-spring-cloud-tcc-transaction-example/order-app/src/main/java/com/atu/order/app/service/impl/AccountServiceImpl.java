package com.atu.order.app.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atu.capital.service.CapitalService;
import com.atu.order.app.service.AccountService;
import com.atu.redpacket.service.RedPacketAccountService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Reference
    RedPacketAccountService redPacketAccountService;

    @Reference
    CapitalService capitalService;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountService.getRedPacketAccountByUserId(userId);
    }

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalService.getCapitalAccountByUserId(userId);
    }
}
