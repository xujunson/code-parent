package com.atu.design.pattern.proxy.staticproxy;

import com.atu.design.pattern.proxy.Animal;
import com.atu.design.pattern.proxy.Person;

/**
 * @Author: Tom
 * @Date: 2019/7/20 10:35
 * @Description: 客户端
 */
public class Test {
    public static void main(String[] args) {
        Animal a = new Person();
        System.out.println("无代理之前正常人吃饭的过程:");
        a.makeFood();
        a.eat();

        System.out.println("代理类介入之后正常人吃饭的过程:");
        Animal b = new Proxy(a);
        b.makeFood();
        b.eat();
    }
}
