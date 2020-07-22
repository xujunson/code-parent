package com.atu.domaincapital.service.impl;

import com.atu.domaincapital.entity.Shop;
import com.atu.domaincapital.repository.ShopRepository;
import com.atu.domaincapital.service.ShopService;
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
