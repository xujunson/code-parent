package com.atu.config;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author: Tom
 * @date: 2021-01-07 21:59
 * @description: @ServletComponentScan 有路径无顺序
 *
 * @Order 注解指定 int 值没有起作用，是无效的
 * @WebFilter 修饰的过滤器在加载时，没有使用 @Order 注解，而是使用的类名来实现自定义Filter顺序
 *
 */

//urlPatterns是一个数组，可以配置拦截多个。urlPatterns= {"*.do","*.jsp"}
@WebFilter(filterName = "FirstAFilter", urlPatterns = "/first")
public class FirstAFilter implements Filter {

    /**
     * 过滤器销毁的时候被调用
     * 该方法在Web容器卸载Filter对象之前被调用，在Filter的整个生命周期中仅会被调用一次。一般用来释放被该Filter对象占用的资源，例如：关闭数据库连接和IO流。
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
        System.out.println("进入FirstAFilter...");
        System.out.println("通过注解方式进入拦截器..............");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("退出FirstAFilter...");
    }

    /**
     * 当容器创建Filter对象时，会调用init方法。init方法在整个生命周期中，只会被调用一次。
     * Web容器在调用整个方法的时候，会传递一个包含了Filter配置信息和运行环境信息的 FilterConfig 对象。
     *
     * @param arg0
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
