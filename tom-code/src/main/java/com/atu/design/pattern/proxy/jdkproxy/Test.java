package com.atu.design.pattern.proxy.jdkproxy;

import com.atu.design.pattern.proxy.Animal;
import com.atu.design.pattern.proxy.Person;

/**
 * @Author: Tom
 * @Date: 2019/7/20 11:14
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy(new Person());
        Animal a = (Animal) dynamicProxy.getAnimalProxy();
        a.makeFood();
        a.eat();
    }
}
