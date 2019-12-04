package com.atu.senior.proxy.cgproxy;

/**
 * @Author: Tom
 * @Date: 2019/7/20 11:58
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        CGProxy proxy = new CGProxy(new CGPerson());
        CGPerson A = proxy.getProxy();
        A.makeFood();
        A.eat();
    }
}
