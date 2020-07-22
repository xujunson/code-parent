package com.atu.domain.order.factory;

import com.atu.domain.order.entity.Order;
import com.atu.domain.order.entity.OrderLine;
import com.atu.domain.order.repository.ProductRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by changming.xie on 4/1/16.
 */
@Component
public class OrderFactory {

    @Resource
    ProductRepository productRepository;

    public Order buildOrder(long payerUserId, long payeeUserId, List<Pair<Long, Integer>> productQuantities) {

        Order order = new Order(payerUserId, payeeUserId);

        for (Pair<Long, Integer> pair : productQuantities) {
            long productId = pair.getLeft();
            order.addOrderLine(new OrderLine(productId, pair.getRight(), productRepository.findById(productId).getPrice()));
        }

        return order;
    }
}
