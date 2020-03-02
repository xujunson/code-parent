package com.tom.controller;

import com.tom.domain.MiaoshaOrder;
import com.tom.domain.MiaoshaUser;
import com.tom.domain.OrderInfo;
import com.tom.result.CodeMsg;
import com.tom.service.GoodsService;
import com.tom.service.MiaoshaService;
import com.tom.service.OrderService;
import com.tom.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author: Tom
 * @date: 2020-02-28 15:11
 * @description: 秒杀控制器
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;


    @Autowired
    MiaoshaService miaoshaService;

    //QPS: 1269-1901
    // 5000 * 10
    @RequestMapping(value = "/do_miaosha")
    public String miaoshad(Model model, MiaoshaUser user,
                          @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);

        if (user == null) {
            return "login";
        }

        //判断商品库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //判断是否已经秒杀到
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //1、减库存，2、下订单，3、写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("goods", goods);
        model.addAttribute("orderInfo", orderInfo);
        return "order_detail";
    }

}
