package com.atu.order.service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-22 16:36
 * @description:
 */
public interface AccountService {
    public BigDecimal getRedPacketAccountByUserId(long userId);
    public BigDecimal getCapitalAccountByUserId(long userId);
}
