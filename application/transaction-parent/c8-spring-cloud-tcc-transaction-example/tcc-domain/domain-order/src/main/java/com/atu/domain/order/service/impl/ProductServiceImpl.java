package com.atu.domain.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.domain.order.entity.Product;
import com.atu.domain.order.repository.ProductRepository;
import com.atu.domain.order.service.ProductService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-22 15:23
 * @description:
 */
@Service
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
