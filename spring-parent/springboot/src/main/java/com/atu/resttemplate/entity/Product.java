package com.atu.resttemplate.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/9 15:57
 * @description :
 */
@Data
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    public Product(Integer id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}