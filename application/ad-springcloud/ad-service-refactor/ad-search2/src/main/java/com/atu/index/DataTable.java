package com.atu.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Tom
 * @date: 2020-03-29 15:19
 * @description: 索引服务的缓存，把索引服务保存在dataTableMap中
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();


    /**
     * 初始化上下文
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    /**
     * PriorityOrdered——优先级排序
     * 初始化java bean的顺序，越小越高
     */
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    public static <T> T of(Class<T> clazz) {
        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }

        dataTableMap.put(clazz, bean(clazz));

        return (T) dataTableMap.get(clazz);
    }

    /**
     * 通过bean名字获取到spring容器的bean
     *
     * @param beanName
     * @param <T>
     * @return
     */
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 第二种方法
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }
}
