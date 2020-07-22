package com.atu.order.domain.service.impl;

import com.atu.order.domain.entity.Order;
import com.atu.order.domain.factory.OrderFactory;
import com.atu.order.domain.repository.OrderLineRepository;
import com.atu.order.domain.repository.OrderRepository;
import com.atu.order.domain.service.OrderDomainService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by changming.xie on 3/25/16.
 */
@DubboService
public class OrderDomainServiceImpl implements OrderDomainService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderLineRepository orderLineRepository;
    @Autowired
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
