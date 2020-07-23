package com.atu.domain.redpacket.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.domain.redpacket.model.TradeOrder;
import com.atu.domain.redpacket.repository.TradeOrderTRepository;
import com.atu.domain.redpacket.service.TradeOrderServiceT;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@Service
public class TradeOrderServiceTImpl implements TradeOrderServiceT {
    @Resource
    TradeOrderTRepository tradeOrderTRepository;

    @Override
    public void insert(TradeOrder tradeOrder) {
        tradeOrderTRepository.save(tradeOrder);
    }

    @Override
    public void update(TradeOrder tradeOrder) {
        tradeOrder.updateVersion();
        TradeOrder order = tradeOrderTRepository.save(tradeOrder);
        if (StringUtils.isEmpty(order)) {
            throw new OptimisticLockingFailureException("update trade order failed");
        }
    }

    @Override
    public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
        return tradeOrderTRepository.findByMerchantOrderNo(merchantOrderNo);
    }
}
