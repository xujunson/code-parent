package com.tom.config;

import com.tom.access.UserContext;
import com.tom.domain.MiaoshaUser;
import com.tom.service.MiaoShaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author: Tom
 * @date: 2020-02-28 11:04
 * @description: 统一管理
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    MiaoShaUserService userService;

    /**
     * 判断Controller层中的参数，是否满足条件，满足条件则执行resolveArgument方法，不满足则跳过
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();

        return clazz == MiaoshaUser.class;
    }

    /**
     * 在supportsParameter方法返回true的情况下才会被调用。
     * 用于处理一些业务，将返回值赋值给Controller层中的这个参数
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return UserContext.getUser();
    }

}
