package com.atu.order.domain.repository;

import com.atu.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


    public Order findByMerchantOrderNo(String merchantOrderNo);
}
