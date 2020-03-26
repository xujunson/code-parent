package com.atu.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author: Tom
 * @date: 2020-03-26 16:34
 * @description:
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {
    //过滤器类型（4种）
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //定义filter执行顺序，数字越小表示顺序越高
    @Override
    public int filterOrder() {
        return 0;
    }

    // 是否启用当前的过滤器，默认是false——不启用
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //上下文 会在整个过滤器执行期间会一直传递下去 传递的都是同一个对象
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());

        return null;
    }
}
