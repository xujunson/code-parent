package com.atu.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author: Tom
 * @date: 2020-03-18 11:15
 * @description: 演示原子数组的使用方法
 *
 *
 */
public class AtomicArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);

        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementr decrementr = new Decrementr(atomicIntegerArray);

        Thread[] threadsIncrementer = new Thread[100];
        Thread[] threadsDecrementer = new Thread[100];

        for (int i = 0; i < 100; i++) {
            threadsDecrementer[i] = new Thread(decrementr);
            threadsIncrementer[i] = new Thread(incrementer);
            threadsDecrementer[i].start();
            threadsIncrementer[i].start();
        }

        //Thread.sleep(10000);
        for (int i = 0; i < 100; i++) {
            try {
                threadsDecrementer[i].join();
                threadsIncrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("发现了错误" + i);
            }
        }
        System.out.println("运行结束");

    }

}

class Decrementr implements Runnable {

    private AtomicIntegerArray array;

    public Decrementr(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i); //自减
        }
    }
}


class Incrementer implements Runnable {

    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i); //自增
        }
    }
}