package com.atu.domain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORD_SHOP")
public class Shop {
    @Id
    @Column(name = "SHOP_ID")
    private long id;
    @Column(name = "OWNER_USER_ID")
    private long ownerUserId;

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public long getId() {
        return id;
    }
}
