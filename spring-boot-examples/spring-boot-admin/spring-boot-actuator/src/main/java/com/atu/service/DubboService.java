package com.atu.service;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Tom
 * @Date: 2021/6/2 3:15 下午
 * @Description:
 */
@Slf4j
public class DubboService {
    public static void checkDubbo() {
        //dubbo线程池数量监控
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.alibaba.dubbo.rpc.protocol.dubbo.status.ThreadPoolStatusChecker");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method check = null;
        try {
            check = clazz.getMethod("check");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Object result = null;
        try {
            result = check.invoke(clazz.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        log.info("result:{}", result.toString());
    }
}
