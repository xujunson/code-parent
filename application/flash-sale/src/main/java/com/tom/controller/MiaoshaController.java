package com.tom.controller;

import com.tom.domain.MiaoshaOrder;
import com.tom.domain.MiaoshaUser;
import com.tom.domain.OrderInfo;
import com.tom.result.CodeMsg;
import com.tom.result.Result;
import com.tom.service.GoodsService;
import com.tom.service.MiaoshaService;
import com.tom.service.OrderService;
import com.tom.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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

    //QPS: 本机 2126
    // 5000 * 10
    /*@RequestMapping(value = "/do_miaosha")
    public String miaosha(Model model, MiaoshaUser user,
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
    }*/

    /**
     * GET POST 区别？
     * GET 幂等：从服务端获取数据，无论掉多少次 产生的结果是一样的，不会对服务端数据产生影响
     * POST 向服务端提交数据
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user,
                                     @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //判断商品库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();

        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        System.out.println(stock);
        //判断是否已经秒杀到
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //1、减库存，2、下订单，3、写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);

        return Result.success(orderInfo);
    }

}
