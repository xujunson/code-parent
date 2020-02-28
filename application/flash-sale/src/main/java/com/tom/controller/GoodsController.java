package com.tom.controller;

import com.tom.domain.MiaoshaUser;
import com.tom.redis.RedisService;
import com.tom.service.MiaoShaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list")
    public String list(Model model, MiaoshaUser user) {

        model.addAttribute("user", user);
        return "goods_list";
    }
}
