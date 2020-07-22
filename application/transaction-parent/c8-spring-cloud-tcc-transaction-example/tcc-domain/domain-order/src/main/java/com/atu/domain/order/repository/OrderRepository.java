package com.atu.domain.order.repository;

import com.atu.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


    public Order findByMerchantOrderNo(String merchantOrderNo);
}
