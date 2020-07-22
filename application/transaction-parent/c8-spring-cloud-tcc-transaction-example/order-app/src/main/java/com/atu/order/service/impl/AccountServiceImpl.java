package com.atu.order.service.impl;

import com.atu.order.service.AccountService;
import com.atu.capital.service.CapitalService;
import com.atu.redpacket.service.RedPacketAccountService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@DubboService
public class AccountServiceImpl implements AccountService {

    @Resource
    RedPacketAccountService redPacketAccountService;

    @Resource
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
