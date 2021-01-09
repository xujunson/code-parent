package com.atu.config;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author: Tom
 * @date: 2021-01-08 13:46
 * @description:
 */
@Order(1)
@WebFilter(filterName = "FirstBFilter", urlPatterns = "/first")
public class FirstBFilter implements Filter {

    /**
     * 过滤器销毁的时候被调用
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /**
     * 在doFilter()方法中，chain.doFilter()前的一般是对request执行的过滤操作，
     * chain.doFilter后面的代码一般是对response执行的操作,
     * chain.doFilter()执行下一个过滤器或者业务处理器。
     * http://192.168.189.1:8080/first
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("进入FirstBFilter...");
        System.out.println("通过注解方式进入拦截器..............");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("退出FirstBFilter...");
    }

    /**
     * 过滤器初始化的被调用
     *
     * @param arg0
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
