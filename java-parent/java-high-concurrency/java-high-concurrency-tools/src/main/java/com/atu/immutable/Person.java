package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 16:19
 * @description: 不可变的对象，演示其他类无法修改这个对象，public也不行
 */
public class Person {
    //一旦被赋值，就没有被更改的可能了
    final int age = 18;
    final String name = "Alice";

    final TestFinal testFinal = new TestFinal();

}
