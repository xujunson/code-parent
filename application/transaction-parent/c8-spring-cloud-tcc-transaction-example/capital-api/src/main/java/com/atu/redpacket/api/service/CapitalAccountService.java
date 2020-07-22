package com.atu.redpacket.api.service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:26
 * @description:
 */
public interface CapitalAccountService {

    BigDecimal getCapitalAccountByUserId(long userId);
}
