package com.atu.domaincapital.service.impl;

import com.atu.domaincapital.model.TradeOrder;
import com.atu.domaincapital.repository.TradeOrderRepository;
import com.atu.domaincapital.service.TradeOrderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
@DubboService
public class TradeOrderServiceImpl implements TradeOrderService {
    @Resource
    TradeOrderRepository tradeOrderRepository;

    @Override
    public void insert(TradeOrder tradeOrder) {
        tradeOrderRepository.save(tradeOrder);
    }

    @Override
    public void update(TradeOrder tradeOrder) {
        tradeOrder.updateVersion();
        TradeOrder order = tradeOrderRepository.save(tradeOrder);
        if (StringUtils.isEmpty(order)) {
            throw new OptimisticLockingFailureException("update trade order failed");
        }
    }

    @Override
    public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
        return tradeOrderRepository.findByMerchantOrderNo(merchantOrderNo);
    }
}
