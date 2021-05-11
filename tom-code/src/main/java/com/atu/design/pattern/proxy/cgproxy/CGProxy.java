package com.atu.design.pattern.proxy.cgproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Tom
 * @Date: 2019/7/20 11:53
 * @Description:
 */
public class CGProxy implements MethodInterceptor {
    CGPerson object;

    public CGProxy(CGPerson o) {
        this.object = o;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
        if (method.getName().equals("makeFood")) {
            System.out.println("CG代理为你做饭！");
            return null;
        } else {
            return proxy.invokeSuper(object, arg);
        }
    }

    public CGPerson getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGPerson.class);
        enhancer.setCallback(this);
        return (CGPerson) enhancer.create();

    }

}
