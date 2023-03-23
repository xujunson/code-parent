package com.atu.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: Tom
 * @date: 2021-01-07 22:04
 * @description: 通过方法完成Filter组件注册
 */
public class SecondAFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * http://192.168.189.1:8080/second
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入SecondAFilter...");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("退出SecondAFilter...");
    }

    @Override
    public void destroy() {

    }
}
