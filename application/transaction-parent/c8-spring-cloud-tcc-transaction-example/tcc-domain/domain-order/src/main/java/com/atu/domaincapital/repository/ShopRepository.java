package com.atu.domaincapital.repository;


import com.atu.domaincapital.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findById(long id);
}
