package com.atu.config;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: Tom
 * @date: 2021-01-08 11:09
 * @description:
 */
public class SecondBFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入SecondBFilter...");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("退出SecondBFilter...");
    }
}
