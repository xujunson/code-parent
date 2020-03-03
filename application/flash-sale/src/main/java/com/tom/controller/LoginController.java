package com.tom.controller;

import com.tom.result.CodeMsg;
import com.tom.result.Result;
import com.tom.service.MiaoShaUserService;
import com.tom.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: Tom
 * @date: 2020-02-27 20:38
 * @description:
 */
@Controller
@RequestMapping("login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoShaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public String doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());

        //使用jsr参数校验
          /*String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        //参数校验
        if (StringUtils.isEmpty(passInput)) {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        String token = userService.login(response, loginVo);
        return token;
    }
}
