package com.atu.senior.Reflect.simpledemo;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 11:29
 * @Description :
 */
public class Person {
    //私有属性
    private String name = "Tom";
    //公有属性
    public int age = 18;
    //构造方法
    public Person() {
    }
    //私有方法
    private void say(){
        System.out.println("private say()...");
    }
    //公有方法
    public void work(){
        System.out.println("public work()...");
    }
}
