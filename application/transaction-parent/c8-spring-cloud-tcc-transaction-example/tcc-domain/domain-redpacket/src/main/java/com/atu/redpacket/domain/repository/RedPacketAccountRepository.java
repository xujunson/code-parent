package com.atu.redpacket.domain.repository;

import com.atu.redpacket.domain.model.RedPacketAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-22 14:25
 * @description:
 */
public interface RedPacketAccountRepository extends JpaRepository<RedPacketAccount, Long> {
    RedPacketAccount findByUserId(long userId);
}
