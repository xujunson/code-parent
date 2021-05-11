package com.atu.design.pattern.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:31
 * @description: 懒汉式(线程不安全)
 */
public class Singleton3 {
    //懒汉————只有在需要用的时候才加载
    private static Singleton3 instance;

    private Singleton3() {
    }

    //线程不安全 多线程竞争多次创建
    public static Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}
