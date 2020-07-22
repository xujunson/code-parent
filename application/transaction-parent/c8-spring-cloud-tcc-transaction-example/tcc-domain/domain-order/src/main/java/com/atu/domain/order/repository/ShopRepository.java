package com.atu.domain.order.repository;


import com.atu.domain.order.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findById(long id);
}
