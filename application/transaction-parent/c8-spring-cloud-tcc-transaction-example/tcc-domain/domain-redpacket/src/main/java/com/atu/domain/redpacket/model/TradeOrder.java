package com.atu.domain.redpacket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "RED_TRADE_ORDER")
public class TradeOrder implements Serializable {

    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "SELF_USER_ID")
    private long selfUserId;
    @Column(name = "OPPOSITE_USER_ID")
    private long oppositeUserId;
    @Column(name = "MERCHANT_ORDER_NO")
    private String merchantOrderNo;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "STATUS")
    private String status = "DRAFT";
    @Column(name = "VERSION")
    private long version = 1l;

    public TradeOrder() {
    }

    public TradeOrder(long selfUserId, long oppositeUserId, String merchantOrderNo, BigDecimal amount) {
        this.selfUserId = selfUserId;
        this.oppositeUserId = oppositeUserId;
        this.merchantOrderNo = merchantOrderNo;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getSelfUserId() {
        return selfUserId;
    }

    public long getOppositeUserId() {
        return oppositeUserId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void confirm() {
        this.status = "CONFIRM";
    }

    public void cancel() {
        this.status = "CANCEL";
    }

    public long getVersion() {
        return version;
    }

    public void updateVersion() {
        version = version + 1;
    }
}
