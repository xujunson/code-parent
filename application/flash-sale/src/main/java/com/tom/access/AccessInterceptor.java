package com.tom.access;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.tom.domain.MiaoshaUser;
import com.tom.redis.AccessKey;
import com.tom.redis.RedisService;
import com.tom.result.CodeMsg;
import com.tom.result.Result;
import com.tom.service.MiaoShaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author: Tom
 * @date: 2020-03-04 10:41
 * @description: 拦截器
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    MiaoShaUserService userService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            MiaoshaUser miaoshaUser = getUser(request, response);
            UserContext.setUser(miaoshaUser); //设置user

            HandlerMethod hm = (HandlerMethod) handler;
            //拿到方法注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if (needLogin) {
                if (miaoshaUser == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + miaoshaUser.getId();
            } else {
                //不需要登录 do nothing
            }

            //重构-改善代码的既有设计
            AccessKey accessKey = AccessKey.withExpire(seconds);
            Integer count = redisService.get(accessKey, key, Integer.class);

            if (count == null) {
                redisService.set(accessKey, key, 1);
            } else if (count < maxCount) {
                redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8"); //防止乱码
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MiaoShaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoShaUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) { //返回登录页面
            return null;
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        //遍历所有cookie
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
