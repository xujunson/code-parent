package com.atu.senior.proxy.test;

/**
 * @author: Tom
 * @date: 2020-05-15 16:53
 * @description:
 */
public class Test {
    //记录日志，再获取飞行时间
    public static void main(String[] args) {
        Bird bird = new Bird();
        BirdLogProxy p1 = new BirdLogProxy(bird);
        BirdTimeProxy p2 = new BirdTimeProxy(p1);
        p2.fly();
    }

   /* public static void main(String[] args) {
        Bird bird = new Bird();
        BirdTimeProxy p2 = new BirdTimeProxy(bird);
        BirdLogProxy p1 = new BirdLogProxy(p2);

        p1.fly();
    }*/
}
