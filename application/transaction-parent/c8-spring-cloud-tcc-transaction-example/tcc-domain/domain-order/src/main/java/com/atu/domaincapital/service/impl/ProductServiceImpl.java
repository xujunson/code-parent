package com.atu.domaincapital.service.impl;

import com.atu.domaincapital.entity.Product;
import com.atu.domaincapital.repository.ProductRepository;
import com.atu.domaincapital.service.ProductService;

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
