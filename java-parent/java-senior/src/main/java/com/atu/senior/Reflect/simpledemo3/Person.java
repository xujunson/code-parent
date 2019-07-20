package com.atu.senior.Reflect.simpledemo3;

/**
 * @Author: ta0567
 * @Date: 2019/7/16 11:38
 * @Description:
 */
public class Person {
    private int age;
    private String name;

    public Person() {

    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
