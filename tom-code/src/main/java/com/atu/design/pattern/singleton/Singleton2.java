package com.atu.design.pattern.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:25
 * @description: 饿汉式(静态代码块) 可用
 */
public class Singleton2 {

    private final static Singleton2 INSTANCE;

    static {
        INSTANCE = new Singleton2();
    }

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        return INSTANCE;
    }

}
