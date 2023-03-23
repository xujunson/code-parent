package com.atu.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Tom
 * @date: 2021-01-07 22:04
 * @description: 有路径有顺序
 */
//@Configuration
public class FilterSecondConfig {

    /**
     * 进入SecondAFilter...
     * 进入SecondBFilter...
     * 退出SecondBFilter...
     * 退出SecondAFilter...
     * @return
     */
    @Bean
    public FilterRegistrationBean secondAFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        SecondAFilter aFilter = new SecondAFilter();
        filterRegistrationBean.setFilter(aFilter);
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setName("AFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean secondBFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        SecondBFilter bFilter = new SecondBFilter();
        filterRegistrationBean.setFilter(bFilter);
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setName("BFilter");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
