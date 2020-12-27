package com.atu.overload;

/**
 * @author: Tom
 * @date: 2020-12-27 10:04
 * @description: 重载
 * JVM在重载方法中，选择合适的目标方法的顺序如下：
 * a、精确匹配
 * b、如果是基本数据类型，自动转换成更大表示范围的基本类型
 * c、通过自动拆箱和装箱
 * d、通过子类向上转型继承路线依次匹配
 * e、通过可变参数匹配
 */

public class OverloadDemo {
    //第一种方法：无参
    public void overloadMethod() {
        System.out.println("无参方法");
    }

    //第二种方法：基本数据类型
    public void methodForOverload(int param) {
        System.out.println("参数为基本类型int的方法");
    }

    public void methodForOverload(long param) {
        System.out.println("参数为基本类型int的方法");
    }

    //第三种方法：包装数据类型
    public void methodForOverload(Integer param) {
        System.out.println("参数为包装数据类型Integer的方法");
    }

    //第四种方法：可变参数,可以接受 0-n 个对象
    public void methodForOverload(Integer... param) {
        System.out.println("可变参数");
    }

    //第五种方法：Object对象
    public void methodForOverload(Object param) {
        System.out.println("参数为Object的方法");
    }

    public void methodForOverload(int param1, Integer param2) {
        System.out.println("参数为int, Integer 的方法");
    }
    public void methodForOverload(Integer param1, int param2) {
        System.out.println("参数为Integer, int的方法");
    }
    public static void main(String[] args) {
        OverloadDemo demo = new OverloadDemo();
        //demo.methodForOverload(1);
        //demo.methodForOverload(12,14);
    }
}


