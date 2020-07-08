package com.atu.axon.account.query;

import com.atu.axon.account.event.AccountCreatedEvent;
import com.atu.axon.account.event.AccountMoneyDepositedEvent;
import com.atu.axon.account.event.AccountMoneyWithdrawEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Tom
 * @date: 2020-07-07 14:08
 * @description: 投影-把聚合对象投影到物化视图上
 */
@Service
public class AccountProjector {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    /**
     * 更新聚合对象
     *
     * @param event
     */
    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountEntity account = new AccountEntity(event.getAccountId(), event.getName());
        accountEntityRepository.save(account);
    }

    @EventHandler
    public void on(AccountMoneyDepositedEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() + event.getAmount());
        accountEntityRepository.save(accountView);
    }

    @EventHandler
    public void on(AccountMoneyWithdrawEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() - event.getAmount());
        accountEntityRepository.save(accountView);
    }
}