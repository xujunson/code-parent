package com.tom.service;

import com.tom.dao.GoodsDao;
import com.tom.dao.OrderDao;
import com.tom.domain.Goods;
import com.tom.domain.MiaoshaUser;
import com.tom.domain.OrderInfo;
import com.tom.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Tom
 * @date: 2020-02-28 15:20
 * @description:
 */
@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;
    /**
     * 原子操作
     *
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOrder(user, goods);
    }
}
