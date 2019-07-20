package com.atu.senior.proxy.staticproxy;

import com.atu.senior.proxy.Animal;

/**
 * @Author: ta0567
 * @Date: 2019/7/20 10:33
 * @Description: 代理类，可以看作是饭馆，他代理人类makeFood的过程，但是吃还是使用的人类的吃方法。
 */
public class Proxy implements Animal {
    public Animal animal;

    public Proxy(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void makeFood() {
        System.out.println("代理类帮你做饭！");
    }

    @Override
    public void eat() {
        animal.eat();
    }
}
