package com.atu.axon.account;

import com.atu.axon.account.command.AccountCreateCommand;
import com.atu.axon.account.command.AccountDepositCommand;
import com.atu.axon.account.command.AccountWithdrawCommand;
import com.atu.axon.account.event.AccountCreatedEvent;
import com.atu.axon.account.event.AccountMoneyDepositedEvent;
import com.atu.axon.account.event.AccountMoneyWithdrawEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * @author: Tom
 * @date: 2020-07-06 22:49
 * @description: 聚合对象
 */
@Aggregate
public class Account {
    /**
     * 主键
     */
    @AggregateIdentifier
    private String accountId;

    /**
     * 余额
     */
    private Double deposit;

    @CommandHandler
    public void handle(AccountDepositCommand command) {
        apply(new AccountMoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(AccountWithdrawCommand command) {
        if (this.deposit >= command.getAmount()) {
            apply(new AccountWithdrawCommand(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }

    @CommandHandler
    public Account(AccountCreateCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.deposit = 0d;
    }

    @EventSourcingHandler
    public void on(AccountMoneyDepositedEvent event) {
        this.deposit += event.getAmount();
    }

    //取款
    @EventSourcingHandler
    public void on(AccountMoneyWithdrawEvent event) {
        this.deposit -= event.getAmount();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}
