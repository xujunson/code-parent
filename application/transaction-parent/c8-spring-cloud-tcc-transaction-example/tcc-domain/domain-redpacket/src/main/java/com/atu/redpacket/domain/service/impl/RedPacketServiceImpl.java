package com.atu.redpacket.domain.service.impl;

import com.atu.redpacket.domain.model.RedPacketAccount;
import com.atu.redpacket.domain.repository.RedPacketAccountRepository;
import com.atu.redpacket.domain.service.RedPacketService;
import com.atu.common.exception.InsufficientBalanceException;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@DubboService
public class RedPacketServiceImpl implements RedPacketService {
    @Resource
    RedPacketAccountRepository redPacketAccountRepository;


    @Override
    public RedPacketAccount findByUserId(long userId) {
        return redPacketAccountRepository.findByUserId(userId);
    }

    @Override
    public void save(RedPacketAccount redPacketAccount) {
        RedPacketAccount account = redPacketAccountRepository.save(redPacketAccount);
        if (ObjectUtils.isEmpty(account)) {
            throw new InsufficientBalanceException();
        }
    }
}
