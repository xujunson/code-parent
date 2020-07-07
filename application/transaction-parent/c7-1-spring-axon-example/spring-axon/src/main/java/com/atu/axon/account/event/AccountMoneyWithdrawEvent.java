package com.atu.axon.account.event;

/**
 * @author: Tom
 * @date: 2020-07-06 22:57
 * @description:
 */
public class AccountMoneyWithdrawEvent {
    private String accountId;

    private Double amount;


    public AccountMoneyWithdrawEvent(String accountId) {
        this.accountId = accountId;
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
