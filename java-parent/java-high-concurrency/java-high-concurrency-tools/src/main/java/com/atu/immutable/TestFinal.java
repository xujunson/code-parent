package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 16:22
 * @description: 测试final能否被修改
 */
public class TestFinal {
    String test;
    public static void main(String[] args) {
        Person person = new Person();
        person.testFinal.test = "HelloWorld";
        System.out.println( person.testFinal.test);
        person.testFinal.test = "HelloWorld hi";
        System.out.println( person.testFinal.test);

    }
}
