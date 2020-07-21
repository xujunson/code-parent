package com.atu.order.repository;

import com.atu.order.entity.Shop;
import com.atu.order.infrastructure.dao.ShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Repository
public class ShopRepository {

    @Autowired
    ShopDao shopDao;

    public Shop findById(long id) {

        return shopDao.findById(id);
    }
}
