package com.tom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-02-28 10:59
 * @description:
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired UserArgumentResolver userArgumentResolver;
    /**
     * 遍历controller 方法参数，并赋值
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
