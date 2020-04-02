package com.atu.extra.dao;


import com.atu.extra.entity.ExtraAd;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>ExtraAd Dao 接口定义</h1>
 */
public interface ExtraAdDao extends JpaRepository<ExtraAd, Long> {
}
