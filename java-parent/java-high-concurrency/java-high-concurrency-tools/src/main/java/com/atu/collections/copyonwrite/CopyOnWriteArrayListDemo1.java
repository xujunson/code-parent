package com.atu.collections.copyonwrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Tom
 * @date: 2020-03-23 14:33
 * @description: 演示CopyOnWriteArrayList可以在迭代的过程中修改数组内容，但是ArrayList不行
 * 对比
 */
public class CopyOnWriteArrayListDemo1 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        //允许在迭代过程中修改，但是迭代的数据还是原来的
        //CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        //迭代器
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println("list is "+list);
            String next = iterator.next();
            System.out.println(next);

            if(next.equals("2")) { //迭代到一半做修改
                list.remove("5");
            }
            if (next.equals("3")) {
                list.add("3 found");
            }
        }

    }
}
