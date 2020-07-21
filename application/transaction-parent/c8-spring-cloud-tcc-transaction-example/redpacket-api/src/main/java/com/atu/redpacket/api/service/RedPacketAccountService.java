package com.atu.redpacket.api.service;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 19:04
 * @description:
 */
public interface RedPacketAccountService {
    BigDecimal getRedPacketAccountByUserId(long userId);
}