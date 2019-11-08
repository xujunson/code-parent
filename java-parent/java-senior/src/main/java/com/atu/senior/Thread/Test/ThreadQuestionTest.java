package com.atu.senior.Thread.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：mark
 * @date ：Created in 2019/10/25 15:08
 * @description：${description}
 */
public class ThreadQuestionTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(Thread.currentThread().getName()+"-------------------"+list.get(i));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"-------------------");
                list.clear();
            }
        });
        thread1.start();
        thread2.start();
    }
}
