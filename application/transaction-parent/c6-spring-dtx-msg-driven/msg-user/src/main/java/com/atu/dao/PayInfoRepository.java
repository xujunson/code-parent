package com.atu.dao;

import com.atu.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {

    PayInfo findOneByOrderId(Long orderId);
}
