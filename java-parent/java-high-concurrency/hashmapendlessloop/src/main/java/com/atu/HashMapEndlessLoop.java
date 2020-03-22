package com.atu;


import java.util.HashMap;

/**
 * @author: Tom
 * @date: 2020-03-22 11:03
 * @description: 演示HashMap在多线程情况下造成死循环的情况
 */
public class HashMapEndlessLoop {
    //初始容量——2, 负载因子——1.5f
    //负载因子会决定你在什么时候扩容
    private static HashMap<Integer, String> map = new HashMap<>(2, 1.5f);

    public static void main(String[] args) {
        map.put(5, "C");
        map.put(7, "B");
        map.put(3, "A");
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(15, "D");
                System.out.println(map);
            }
        }, "Thread 1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(1, "E");
                System.out.println(map);
            }
        }, "Thread 2").start();
    }
}
