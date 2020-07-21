package com.atu.capital.repository;

import com.atu.capital.common.InsufficientBalanceException;
import com.atu.capital.entity.RedPacketAccount;
import com.atu.capital.infrastructure.dao.RedPacketAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Repository
public class RedPacketAccountRepository {

    @Autowired
    RedPacketAccountDao redPacketAccountDao;

    public RedPacketAccount findByUserId(long userId) {

        return redPacketAccountDao.findByUserId(userId);
    }

    public void save(RedPacketAccount redPacketAccount) {
        int effectCount = redPacketAccountDao.update(redPacketAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
