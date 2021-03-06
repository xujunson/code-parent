package com.atu.domain.capital.repository;

import com.atu.domain.capital.model.CapitalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-22 14:25
 * @description:
 */
public interface CapitalAccountRepository extends JpaRepository<CapitalAccount, Long> {
    CapitalAccount findByUserId(long userId);
}
