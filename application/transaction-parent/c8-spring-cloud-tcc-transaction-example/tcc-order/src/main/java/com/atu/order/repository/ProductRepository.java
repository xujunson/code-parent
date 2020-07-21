package com.atu.order.repository;


import com.atu.order.entity.Product;
import com.atu.order.infrastructure.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Repository
public class ProductRepository {

    @Autowired
    ProductDao productDao;

    public Product findById(long productId) {
        return productDao.findById(productId);
    }

    public List<Product> findByShopId(long shopId) {
        return productDao.findByShopId(shopId);
    }
}
