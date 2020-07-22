package com.atu.redpacket.service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:26
 * @description:
 */
public interface CapitalService {

    BigDecimal getCapitalAccountByUserId(long userId);
}
