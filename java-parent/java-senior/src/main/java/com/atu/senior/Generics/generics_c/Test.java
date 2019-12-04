package com.atu.senior.Generics.generics_c;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/3 21:36
 * @description：${description}
 */
public class Test {
    public static void main(String[] args) {
        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
        //传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<Integer>(123456);

        //传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> genericString = new Generic<String>("key_vlaue");
        System.out.println("泛型测试" + "key is " + genericInteger.getKey());
        System.out.println("泛型测试" + "key is " + genericString.getKey());

        //如果不传入泛型类型实参的话，在泛型类中使用泛型的方法或成员变量定义的类型可以为任何的类型。
        Generic generic = new Generic("111111");
        Generic generic1 = new Generic(4444);
        Generic generic2 = new Generic(55.55);
        Generic generic3 = new Generic(false);

        System.out.println("泛型测试"+"key is " + generic.getKey());
        System.out.println("泛型测试"+"key is " + generic1.getKey());
        System.out.println("泛型测试"+"key is " + generic2.getKey());
        System.out.println("泛型测试"+"key is " + generic3.getKey());
    }
}
