package com.atu.capital.entity;

import com.atu.capital.common.InsufficientBalanceException;

import java.math.BigDecimal;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
public class CapitalAccount {

    private long id;

    private long userId;

    private BigDecimal balanceAmount;

    private BigDecimal transferAmount = BigDecimal.ZERO;

    public long getUserId() {
        return userId;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void transferFrom(BigDecimal amount) {

        this.balanceAmount = this.balanceAmount.subtract(amount);

        if (BigDecimal.ZERO.compareTo(this.balanceAmount) > 0) {
            throw new InsufficientBalanceException();
        }

        transferAmount = transferAmount.add(amount.negate());
    }

    public void transferTo(BigDecimal amount) {
        this.balanceAmount = this.balanceAmount.add(amount);
        transferAmount = transferAmount.add(amount);
    }

    public void cancelTransfer(BigDecimal amount) {
        transferTo(amount);
    }
}
