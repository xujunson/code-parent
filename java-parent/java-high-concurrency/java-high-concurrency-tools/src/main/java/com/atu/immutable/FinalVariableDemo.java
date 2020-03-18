package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 16:45
 * @description: 演示final变量
 */
public class FinalVariableDemo {
    // 类中的final属性赋值
    //1、直接赋值
    private final int a = 6;

    //2、构造函数赋值
    private final int b;

    public FinalVariableDemo(int b) {
        this.b = b;
    }

    //3、代码块赋值
    private final int c;

    {
        c = 7;
    }


    //类中的static final属性
    //1、直接赋值
    private static final int d = 1;

    //2、static初始代码块赋值
    private static final int e;

    static {
        e = 1;
    }

    //使用前赋值
    void testFinal() {
        final int f;
    }
}
