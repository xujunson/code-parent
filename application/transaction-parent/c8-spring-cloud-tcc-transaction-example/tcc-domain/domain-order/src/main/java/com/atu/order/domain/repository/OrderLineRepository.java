package com.atu.order.domain.repository;

import com.atu.order.domain.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-22 15:09
 * @description:
 */
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
