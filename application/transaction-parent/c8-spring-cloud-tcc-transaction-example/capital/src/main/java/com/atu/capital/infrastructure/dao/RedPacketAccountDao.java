package com.atu.capital.infrastructure.dao;


import com.atu.capital.entity.RedPacketAccount;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
public interface RedPacketAccountDao {

    RedPacketAccount findByUserId(long userId);

    int update(RedPacketAccount redPacketAccount);
}
