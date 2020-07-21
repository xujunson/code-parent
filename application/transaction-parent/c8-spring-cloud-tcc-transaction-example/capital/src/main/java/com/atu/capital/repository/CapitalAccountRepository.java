package com.atu.capital.repository;

import com.atu.capital.common.InsufficientBalanceException;
import com.atu.capital.entity.CapitalAccount;
import com.atu.capital.infrastructure.dao.CapitalAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@Repository
public class CapitalAccountRepository {

    @Autowired
    CapitalAccountDao capitalAccountDao;

    public CapitalAccount findByUserId(long userId) {

        return capitalAccountDao.findByUserId(userId);
    }

    public void save(CapitalAccount capitalAccount) {
        int effectCount = capitalAccountDao.update(capitalAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
