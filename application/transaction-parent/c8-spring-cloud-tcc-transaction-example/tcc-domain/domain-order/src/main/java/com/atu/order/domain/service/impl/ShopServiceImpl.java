package com.atu.order.domain.service.impl;

import com.atu.order.domain.entity.Shop;
import com.atu.order.domain.repository.ShopRepository;
import com.atu.order.domain.service.ShopService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 15:23
 * @description:
 */
@DubboService
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopRepository shopRepository;

    @Override
    public Shop findById(long id) {
        return shopRepository.findById(id);
    }
}
