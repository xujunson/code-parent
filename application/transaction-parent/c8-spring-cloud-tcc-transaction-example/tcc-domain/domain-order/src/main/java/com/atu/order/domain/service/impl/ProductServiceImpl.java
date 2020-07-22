package com.atu.order.domain.service.impl;

import com.atu.order.domain.entity.Product;
import com.atu.order.domain.repository.ProductRepository;
import com.atu.order.domain.service.ProductService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-22 15:23
 * @description:
 */
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductRepository productRepository;

    @Override
    public Product findById(long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> findByShopId(long shopId) {
        return productRepository.findByShopId(shopId);
    }

}
