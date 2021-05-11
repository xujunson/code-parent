package com.atu.design.pattern.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:25
 * @description: 饿汉式(静态常量) 可用
 */
public class Singleton1 {
    //优点：写法相对简单，在类装载的时候就完成了实例化
    private final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

}
