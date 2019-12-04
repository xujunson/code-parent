package com.atu.senior.proxy.jdkproxy;

import com.atu.senior.proxy.Animal;
import com.atu.senior.proxy.Person;

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
