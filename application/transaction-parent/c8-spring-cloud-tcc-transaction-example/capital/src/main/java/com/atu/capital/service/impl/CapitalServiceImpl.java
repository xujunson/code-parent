package com.atu.capital.service.impl;

import com.atu.capital.domain.service.CapitalAccountService;
import com.atu.capital.service.CapitalService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@DubboService
public class CapitalServiceImpl implements CapitalService {

    @Resource
    CapitalAccountService capitalAccountService;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountService.findByUserId(userId).getBalanceAmount();
    }
}