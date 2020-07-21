package com.atu.capital.service.impl;

import com.atu.capital.api.service.CapitalAccountService;
import com.atu.capital.repository.CapitalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService {

    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}