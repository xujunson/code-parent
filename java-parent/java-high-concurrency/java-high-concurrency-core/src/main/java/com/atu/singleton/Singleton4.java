package com.atu.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:31
 * @description: 懒汉式(线程安全) 不推荐
 */
public class Singleton4 {
    //懒汉————只有在需要用的时候才加载
    private static Singleton4 instance;

    private Singleton4() {
    }

    //虽然线程安全 但是效率低
    public synchronized static Singleton4 getInstance() {
        if (instance == null) {
            instance = new Singleton4();
        }
        return instance;
    }
}
