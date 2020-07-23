package com.atu.domain.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atu.domain.order.entity.Shop;
import com.atu.domain.order.repository.ShopRepository;
import com.atu.domain.order.service.ShopService;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 15:23
 * @description:
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopRepository shopRepository;

    @Override
    public Shop findById(long id) {
        return shopRepository.findById(id);
    }
}
