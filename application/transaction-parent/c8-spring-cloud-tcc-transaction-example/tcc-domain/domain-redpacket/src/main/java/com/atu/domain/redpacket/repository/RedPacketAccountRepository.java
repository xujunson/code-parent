package com.atu.domain.redpacket.repository;

import com.atu.domain.redpacket.model.RedPacketAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-22 14:25
 * @description:
 */
public interface RedPacketAccountRepository extends JpaRepository<RedPacketAccount, Long> {
    RedPacketAccount findByUserId(long userId);
}
