package com.atu.design.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Tom
 * @Date: 2019/7/20 11:09
 * @Description: JDK自带的动态代理
 */
public class DynamicProxy implements InvocationHandler {
    private Object object;

    public DynamicProxy(Object animal) {
        this.object = animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("makeFood")) {
            System.out.println("动态代理帮你做饭");
            return null;
        } else {
            return method.invoke(object, args);
        }

    }

    public Object getAnimalProxy() {
        System.out.println(object.getClass().getInterfaces());
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }
}