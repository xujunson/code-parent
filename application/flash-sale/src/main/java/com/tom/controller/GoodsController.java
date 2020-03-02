package com.tom.controller;

import com.tom.domain.MiaoshaUser;
import com.tom.redis.GoodsKey;
import com.tom.redis.RedisService;
import com.tom.service.GoodsService;
import com.tom.service.MiaoShaUserService;
import com.tom.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-02-28 10:15
 * @description: 商品控制器
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoShaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 1、版本一：兼容手机端通过参数传递token ，增加 paramToken
     *
     * @param model
     * @param cookieToken
     * @param paramToken
     * @return
     */
    /*@RequestMapping(value = "/to_list")
    public String list(HttpServletResponse response, Model model, @CookieValue(value = MiaoShaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                       @RequestParam(value = MiaoShaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {

        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) { //返回登录页面
            return "login";
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser user = userService.getByToken(response, token);
        model.addAttribute("user", user);
        return "goods_list";
    }*/

    /**
     * 版本二：清爽版
     * QPS 2125 1947
     * 5000 * 10
     *
     * @param model
     * @param user
     * @return
     */
    /*@RequestMapping(value = "/to_list")
    public String list(Model model, MiaoshaUser user) {

        model.addAttribute("user", user);

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }*/

    /**
     * 版本三：页面缓存
     * QPS 3011 2740
     * 5000 * 10
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {

        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) { //取缓存
            return html;
        }
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        //return "goods_list";
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    /**
     * 商品详情版本一
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    /*@RequestMapping(value = "/to_detail/{goodsId}")
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                         @PathVariable("goodsId") long goodsId) {
        //snowflake 算法-生成id

        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        *//*GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);*//*
        return "goods_detail";
    }*/

    /**
     * url缓存
     *
     * @param request
     * @param response
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                         @PathVariable("goodsId") long goodsId) {
        //snowflake 算法-生成id

        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsList, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) { //取缓存
            return html;
        }

        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //return "goods_detail";
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }

        return html;
    }
}
