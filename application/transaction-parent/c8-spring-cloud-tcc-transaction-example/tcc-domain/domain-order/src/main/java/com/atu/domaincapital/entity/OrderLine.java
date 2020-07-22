package com.atu.domaincapital.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 2300754647209250837L;
    @Id
    private long id;

    private long productId;

    private int quantity;

    private BigDecimal unitPrice;

    public OrderLine() {

    }

    public OrderLine(Long productId, Integer quantity,BigDecimal unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public long getId() {
        return id;
    }
}
