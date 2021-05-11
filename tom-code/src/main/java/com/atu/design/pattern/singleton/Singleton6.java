package com.atu.design.pattern.singleton;

/**
 * @author: Tom
 * @date: 2020-03-12 22:31
 * @description: 双重检查——推荐面试使用
 */
public class Singleton6 {
    //懒汉————只有在需要用的时候才加载
    private volatile static Singleton6 instance;

    private Singleton6() {
    }

    //线程不安全 创建多个实例
    public static Singleton6 getInstance() {
        if (instance == null) {
            synchronized (Singleton6.class) {
                if (instance == null) {
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
