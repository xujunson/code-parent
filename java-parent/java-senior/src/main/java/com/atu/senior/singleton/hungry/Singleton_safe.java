package com.atu.senior.singleton.hungry;

/**
 * @author ：mark
 * @date ：Created in 2019/11/18 14:42
 * @description： 单例——饿汉模式---线程安全
 */
//在类加载时就完成了初始化，所以类加载较慢，但是获取对象的速度快
public class Singleton_safe {
    //用静态变量来存储唯一实例
    private static final Singleton_safe instance = new Singleton_safe();

    //私有化构造函数
    private Singleton_safe() {
        //里面可能有很多操作
        System.out.println("嘿嘿，我创建了！");
    }

    //提供一个公共的静态方法，用来返回唯一实例
    public static Singleton_safe getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        getInstance();
    }
}
