package com.atu.capital.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atu.capital.service.CapitalService;
import com.atu.domain.capital.service.CapitalAccountService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@Service
public class CapitalServiceImpl implements CapitalService {

    @Reference
    CapitalAccountService capitalAccountService;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountService.findByUserId(userId).getBalanceAmount();
    }
}