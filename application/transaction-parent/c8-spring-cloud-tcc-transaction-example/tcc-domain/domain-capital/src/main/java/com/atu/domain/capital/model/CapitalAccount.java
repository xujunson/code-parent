package com.atu.domain.capital.model;

import com.atu.common.exception.InsufficientBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "CAP_CAPITAL_ACCOUNT")
public class CapitalAccount {

    @Id
    @Column(name = "CAPITAL_ACCOUNT_ID")
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "BALANCE_AMOUNT")
    private BigDecimal balanceAmount;

    @Transient
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
