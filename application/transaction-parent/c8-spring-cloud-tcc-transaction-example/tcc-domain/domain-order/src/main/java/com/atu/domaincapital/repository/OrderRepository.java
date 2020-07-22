package com.atu.domaincapital.repository;

import com.atu.domaincapital.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


    public Order findByMerchantOrderNo(String merchantOrderNo);
}
