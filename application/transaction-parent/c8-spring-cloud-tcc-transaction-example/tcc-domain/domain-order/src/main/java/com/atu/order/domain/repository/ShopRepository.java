package com.atu.order.domain.repository;


import com.atu.order.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findById(long id);
}
