package com.atu.immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Tom
 * @date: 2020-03-18 17:21
 * @description: 一个属性是对象，但是整体不可变，其他类无法修改set里面恶毒数据
 */
public class ImmutableDemo {
    //不可变指的是 内容不可变
    //1、private属性 外面的类访问不到
    private final Set<String> students = new HashSet<>();
    public ImmutableDemo() {
        students.add("李晓飞");
        students.add("王一");
        students.add("六二");
    }

    //2、其他的方法只是读取
    public  boolean isStudent(String name) {
        return students.contains(name);
    }

}
