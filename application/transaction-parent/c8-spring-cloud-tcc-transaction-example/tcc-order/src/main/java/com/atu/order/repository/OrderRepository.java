package com.atu.order.repository;


import com.atu.order.entity.Order;
import com.atu.order.entity.OrderLine;
import com.atu.order.infrastructure.dao.OrderDao;
import com.atu.order.infrastructure.dao.OrderLineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Repository
public class OrderRepository {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderLineDao orderLineDao;

    public void createOrder(Order order) {
        orderDao.insert(order);

        for (OrderLine orderLine : order.getOrderLines()) {
            orderLineDao.insert(orderLine);
        }
    }

    public void updateOrder(Order order) {
        order.updateVersion();
        int effectCount = orderDao.update(order);

        if (effectCount < 1) {
            throw new OptimisticLockingFailureException("update order failed");
        }
    }

    public Order findByMerchantOrderNo(String merchantOrderNo) {
        return orderDao.findByMerchantOrderNo(merchantOrderNo);
    }
}
