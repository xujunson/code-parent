package com.atu.order.service;

import org.mengyun.tcctransaction.api.UniqueIdentity;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-22 16:36
 * @description:
 */
public interface PaymentService {
    public void makePayment(@UniqueIdentity String orderNo, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount);
}
