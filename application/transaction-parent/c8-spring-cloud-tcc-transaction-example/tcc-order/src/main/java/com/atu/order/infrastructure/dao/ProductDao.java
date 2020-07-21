package com.atu.order.infrastructure.dao;

import com.atu.order.entity.Product;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
public interface ProductDao {

    Product findById(long productId);

    List<Product> findByShopId(long shopId);
}
