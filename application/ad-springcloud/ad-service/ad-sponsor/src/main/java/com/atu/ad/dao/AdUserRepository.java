package com.atu.ad.dao;

import com.atu.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Tom
 * @date: 2020-03-27 16:35
 * @description:
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {
    /**
     * 根据用户名查找用户记录
     *
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
