package com.atu.design.pattern.proxy;

/**
 * @Author: Tom
 * @Date: 2019/7/20 10:32
 * @Description: 人类，继承动物接口，实现每个方法
 */
public class Person implements Animal {
    @Override
    public void makeFood() {
        System.out.println("自己在做饭");

    }

    @Override
    public void eat() {
        System.out.println("吃！");

    }
}
