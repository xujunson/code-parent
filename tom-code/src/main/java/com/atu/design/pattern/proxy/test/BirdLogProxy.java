package com.atu.design.pattern.proxy.test;

/**
 * @author: Tom
 * @date: 2020-05-15 16:52
 * @description: 代理类
 */
public class BirdLogProxy implements Flyable {
    private Flyable flyable;

    public BirdLogProxy(Flyable flyable) {
        this.flyable = flyable;
    }

    @Override
    public void fly() {
        System.out.println("Bird fly start...");

        flyable.fly();

        System.out.println("Bird fly end...");
    }
}
