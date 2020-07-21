package com.atu.capital.infrastructure.dao;

import com.atu.capital.entity.CapitalAccount;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
public interface CapitalAccountDao {

    CapitalAccount findByUserId(long userId);

    int update(CapitalAccount capitalAccount);
}
