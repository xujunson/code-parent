package com.atu.axon.account.event;

/**
 * @author: Tom
 * @date: 2020-07-06 22:56
 * @description:
 */
public class AccountCreatedEvent {
    private String accountId;
    private String name;

    public AccountCreatedEvent(String accountId, String name) {
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
