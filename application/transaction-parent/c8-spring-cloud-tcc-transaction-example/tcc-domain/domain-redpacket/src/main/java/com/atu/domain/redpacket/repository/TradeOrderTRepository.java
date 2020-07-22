package com.atu.domain.redpacket.repository;

import com.atu.domain.redpacket.model.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-22 14:25
 * @description:
 */
public interface TradeOrderTRepository extends JpaRepository<TradeOrder, Long> {
    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
