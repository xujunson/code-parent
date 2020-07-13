package com.atu.proxy;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

/**
 * 把order服务的get请求转发的order-query上——实现读写分离
 */
@Component
public class HttpMethodZuulFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpMethodZuulFilter.class);

    private static final String REQUEST_PATH = "/order";
    private static final String TARGET_SERVICE = "order-query";

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    /**
     * 顺讯
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否启用过滤器的条件
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        //当请求前缀是order、类型是GET的时候，把它转发到order-query上
        return "GET".equals(method.toUpperCase()) && requestURI.startsWith(REQUEST_PATH);
    }

    /**
     * 转发
     *
     * @return
     */
    @Override
    public Object run() {
        LOG.info("Route GET order requests to order-query service.");
        RequestContext context = RequestContext.getCurrentContext();
        context.set("serviceId", TARGET_SERVICE);
        context.setRouteHost(null);
        return null;
    }
}
