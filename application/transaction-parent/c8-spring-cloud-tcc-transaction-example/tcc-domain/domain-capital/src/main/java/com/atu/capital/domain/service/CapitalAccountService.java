package com.atu.capital.domain.service;

import com.atu.capital.domain.model.CapitalAccount;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
public interface CapitalAccountService {
    public CapitalAccount findByUserId(long userId);

    public void save(CapitalAccount capitalAccount);
}
