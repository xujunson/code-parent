package com.atu.domain.order.service.impl;

import com.atu.domain.order.entity.Order;
import com.atu.domain.order.factory.OrderFactory;
import com.atu.domain.order.repository.OrderRepository;
import com.atu.domain.order.repository.OrderLineRepository;
import com.atu.domain.order.service.OrderDomainService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by changming.xie on 3/25/16.
 */
@DubboService
public class OrderDomainServiceImpl implements OrderDomainService {

    @Resource
    OrderRepository orderRepository;
    @Resource
    OrderLineRepository orderLineRepository;
    @Resource
    OrderFactory orderFactory;

    @Transactional
    @Override
    public Order createOrder(long payerUserId, long payeeUserId, List<Pair<Long, Integer>> productQuantities) {
        Order order = orderFactory.buildOrder(payerUserId, payeeUserId, productQuantities);

        orderRepository.save(order);
        orderLineRepository.saveAll(order.getOrderLines());

        return order;
    }

    @Override
    public void updateOrder(Order order) {
        order.updateVersion();
        Order orderSave = orderRepository.save(order);

        if (ObjectUtils.isEmpty(orderSave)) {
            throw new OptimisticLockingFailureException("update order failed");
        }
    }

    @Override
    public Order findOrderByMerchantOrderNo(String orderNo) {
        return orderRepository.findByMerchantOrderNo(orderNo);
    }
}
