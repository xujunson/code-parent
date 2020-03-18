package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 17:39
 * @description:
 */
public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c)); //true
        System.out.println((a == e)); //false

        //因为一旦把b，加了final之后编译期间，我们就知道它的准确值了，而且这个b永远不会变化；
        //所以后期编译器会把它当做编译时期的常量使用，所以c就没有必要新建一个对象了，就会和a指向一样的地址

        //而d最开始是指向常量池中的悟空，而不是悟空2，而且由于d没有被final修饰，所以编译器在使用的时候
        //不会知道具体的值，所以e的值也只能在运行时确定，像这种在运行时确定的e，它会在堆上生成 悟空2，
        //所以e指向的是堆上的，而a、c指向的是常量池的
    }
}
