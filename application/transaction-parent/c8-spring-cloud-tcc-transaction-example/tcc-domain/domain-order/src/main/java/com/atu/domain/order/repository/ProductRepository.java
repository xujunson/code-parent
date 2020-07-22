package com.atu.domain.order.repository;

import com.atu.domain.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findById(long productId);

    public List<Product> findByShopId(long shopId);
}
