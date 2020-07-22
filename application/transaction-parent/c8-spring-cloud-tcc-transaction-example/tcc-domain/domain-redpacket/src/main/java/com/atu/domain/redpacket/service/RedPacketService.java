package com.atu.domain.redpacket.service;

import com.atu.domain.redpacket.model.RedPacketAccount;

/**
 * @author: Tom
 * @date: 2020-07-22 14:27
 * @description:
 */
public interface RedPacketService {
    public RedPacketAccount findByUserId(long userId);
    public void save(RedPacketAccount redPacketAccount);
}
