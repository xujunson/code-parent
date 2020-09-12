package com.atu.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Tom
 * @date: 2020-09-12 14:28
 * @description:
 */
public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        //1、获取类
        Class rc = Class.forName("com.atu.reflect.Robot");
        //2、转换
        Robot r = (Robot) rc.newInstance();
        System.out.println("Class name is " + rc.getName());

        //getDeclaredMethod获取方法,包括public private 不能获取继承和实现接口的方法
        Method getHello = rc.getDeclaredMethod("throwHello", String.class);

        //私有方法设值为true，否则报错
        getHello.setAccessible(true);
        Object str = getHello.invoke(r, "Tom");
        System.out.println("getHello result is " + str);

        //getMethod只能获取public方法 不能获取private方法，但是可以获取继承类的方法以及实现接口的方法
        Method sayHi = rc.getMethod("sayHi", String.class);
        sayHi.invoke(r, "Welcome");

        //获取私有类型的Field
        Field name = rc.getDeclaredField("name");
        name.setAccessible(true);
        name.set(r, "XuJunson");
        sayHi.invoke(r, "Welcome");
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }
}
