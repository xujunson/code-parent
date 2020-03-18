package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 17:39
 * @description:
 */
public class FinalStringDemo2 {
    public static void main(String[] args) {
        String a = "悟空2";
        final String b = getDaShiXiong();
        String c = b + 2;
        System.out.println((a == c)); //false

        //问题在于b是通过方法得到的，编译器没办法确定b的值，编译器也没办法优化，所以c也是在运行时才生成的
    }

    private static String getDaShiXiong() {
        return "悟空";
    }
}
