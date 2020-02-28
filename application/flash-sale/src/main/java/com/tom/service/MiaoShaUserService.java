package com.tom.service;

import com.tom.dao.MiaoShaUserDao;
import com.tom.domain.MiaoshaUser;
import com.tom.exception.GlobalException;
import com.tom.redis.MiaoshaUserKey;
import com.tom.redis.RedisService;
import com.tom.result.CodeMsg;
import com.tom.util.MD5Util;
import com.tom.util.UUIDUtil;
import com.tom.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Tom
 * @date: 2020-02-27 21:17
 * @description:
 */
@Service
public class MiaoShaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    MiaoShaUserDao miaoShaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoShaUserDao.getById(id);
    }


    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) { //public方法一定要做参数验证
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);

        if (user != null) {
            //延长有效期
            addCookie(response, user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String fromPass = loginVo.getPassword();
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        //1、判断手机号是否存在
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //2、验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDb = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(fromPass, saltDb);

        if (!dbPass.equals(calcPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //3、登录成功生成cookie
        addCookie(response, miaoshaUser);
        return true;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser miaoshaUser) {
        String token = UUIDUtil.uuid();
        //把token和用户信息绑定到redis中
        redisService.set(MiaoshaUserKey.token, token, miaoshaUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds()); //有效期
        cookie.setPath("/"); //根目录
        response.addCookie(cookie); //把cookie写到客户端
    }

}
