package com.atu.domain.order.service;

import com.atu.domain.order.entity.Product;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-22 15:23
 * @description:
 */
public interface ProductService {
    public Product findById(long productId);
    public List<Product> findByShopId(long shopId);
}
