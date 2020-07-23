package com.atu.domain.capital.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.common.exception.InsufficientBalanceException;
import com.atu.domain.capital.model.CapitalAccount;
import com.atu.domain.capital.repository.CapitalAccountRepository;
import com.atu.domain.capital.service.CapitalAccountService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@Service
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
