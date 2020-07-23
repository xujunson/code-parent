package com.atu.domain.redpacket.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.domain.redpacket.model.RedPacketAccount;
import com.atu.domain.redpacket.repository.RedPacketAccountRepository;
import com.atu.domain.redpacket.service.RedPacketService;
import com.atu.common.exception.InsufficientBalanceException;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@Service
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
