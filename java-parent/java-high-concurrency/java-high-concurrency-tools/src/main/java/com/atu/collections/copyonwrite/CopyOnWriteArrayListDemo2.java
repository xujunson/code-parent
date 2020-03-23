package com.atu.collections.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Tom
 * @date: 2020-03-23 14:58
 * @description: 对比两个迭代器
 */
public class CopyOnWriteArrayListDemo2 {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});

        System.out.println(list);

        Iterator<Integer> itr1 = list.iterator();
        list.add(4);
        System.out.println(list);
        Iterator<Integer> itr2 = list.iterator();

        itr1.forEachRemaining(System.out::println);

        itr2.forEachRemaining(System.out::println);
    }
}
