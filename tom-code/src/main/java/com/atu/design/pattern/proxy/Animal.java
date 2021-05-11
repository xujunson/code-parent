package com.atu.design.pattern.proxy;

/**
 * @Author: Tom
 * @Date: 2019/7/20 10:29
 * @Description: 动物的接口，每个动物都需要制作食物，然后开吃
 */
public interface Animal {

    /**
     * 制作食物
     */
    void makeFood();

    /**
     * 开吃
     */
    void eat();
}
