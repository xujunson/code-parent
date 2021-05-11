package com.atu.design.pattern.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:31
 * @description: 懒汉式(线程不安全) 不推荐
 */
public class Singleton5 {
    //懒汉————只有在需要用的时候才加载
    private static Singleton5 instance;

    private Singleton5() {
    }

    //线程不安全 创建多个实例
    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized (Singleton5.class) {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
