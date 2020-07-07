package com.atu.axon.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author: Tom
 * @date: 2020-07-06 22:53
 * @description: 关联聚合对象
 */
public class AccountCreateCommand {

    @TargetAggregateIdentifier
    private String accountId;

    private String name;

    public AccountCreateCommand(String accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
