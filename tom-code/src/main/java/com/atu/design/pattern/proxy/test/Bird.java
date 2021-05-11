package com.atu.design.pattern.proxy.test;

import java.util.Random;

/**
 * @author: Tom
 * @date: 2020-05-15 16:54
 * @description: 委托类
 */
public class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Bird is flying...");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

