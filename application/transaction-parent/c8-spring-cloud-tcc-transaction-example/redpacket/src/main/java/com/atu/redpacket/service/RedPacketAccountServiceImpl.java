package com.atu.redpacket.service;

import com.atu.capital.repository.RedPacketAccountRepository;
import com.atu.redpacket.api.service.RedPacketAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Service("redPacketAccountService")
public class RedPacketAccountServiceImpl implements RedPacketAccountService {

    @Autowired
    RedPacketAccountRepository redPacketAccountRepository;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
