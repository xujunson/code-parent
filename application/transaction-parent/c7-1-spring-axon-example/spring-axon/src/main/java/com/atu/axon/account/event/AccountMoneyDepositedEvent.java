package com.atu.axon.account.event;

/**
 * @author: Tom
 * @date: 2020-07-06 22:57
 * @description:
 */
public class AccountMoneyDepositedEvent {
    private String accountId;

    private Double amount;

    public AccountMoneyDepositedEvent(String accountId, Double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
