package com.atu.domain.capital.service;

import com.atu.domain.capital.model.CapitalAccount;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
public interface CapitalAccountService {
    public CapitalAccount findByUserId(long userId);

    public void save(CapitalAccount capitalAccount);
}
