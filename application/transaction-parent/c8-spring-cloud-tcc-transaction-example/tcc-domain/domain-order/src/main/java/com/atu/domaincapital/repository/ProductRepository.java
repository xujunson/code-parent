package com.atu.domaincapital.repository;

import com.atu.domaincapital.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findById(long productId);

    public List<Product> findByShopId(long shopId);
}
