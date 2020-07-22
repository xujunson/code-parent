package com.atu.order.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shop {
    @Id
    private long id;

    private long ownerUserId;

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public long getId() {
        return id;
    }
}
