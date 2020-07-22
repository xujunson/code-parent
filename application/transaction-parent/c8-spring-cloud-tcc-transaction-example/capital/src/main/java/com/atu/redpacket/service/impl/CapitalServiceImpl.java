package com.atu.redpacket.service.impl;

import com.atu.capital.domain.model.CapitalAccount;
import com.atu.capital.domain.service.CapitalAccountService;
import com.atu.redpacket.service.CapitalService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@DubboService
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    CapitalAccountService capitalAccountService;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountService.findByUserId(userId).getBalanceAmount();
    }
}