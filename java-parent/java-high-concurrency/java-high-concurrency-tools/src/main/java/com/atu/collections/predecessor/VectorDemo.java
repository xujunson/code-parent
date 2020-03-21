package com.atu.collections.predecessor;

import java.util.Vector;

/**
 * @author: Tom
 * @date: 2020-03-21 18:15
 * @description: 演示Vector，主要是看Vector源码
 */
public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
