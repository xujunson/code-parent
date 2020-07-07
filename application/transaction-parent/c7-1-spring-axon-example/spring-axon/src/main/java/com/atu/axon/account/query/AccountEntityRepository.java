package com.atu.axon.account.query;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-07-07 14:08
 * @description:
 */
public interface AccountEntityRepository extends JpaRepository<AccountEntity, String> {
}

