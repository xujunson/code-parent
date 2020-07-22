package com.atu.domaincapital.service.impl;

import com.atu.common.exception.InsufficientBalanceException;
import com.atu.domaincapital.model.CapitalAccount;
import com.atu.domaincapital.repository.CapitalAccountRepository;
import com.atu.domaincapital.service.CapitalAccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@DubboService
public class CapitalAccountServiceImpl implements CapitalAccountService {
    @Resource
    CapitalAccountRepository capitalAccountRepository;


    @Override
    public CapitalAccount findByUserId(long userId) {
        return capitalAccountRepository.findByUserId(userId);
    }

    @Override
    public void save(CapitalAccount redPacketAccount) {
        CapitalAccount account = capitalAccountRepository.save(redPacketAccount);
        if (ObjectUtils.isEmpty(account)) {
            throw new InsufficientBalanceException();
        }
    }
}
