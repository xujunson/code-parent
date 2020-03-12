package com.atu.jmm;

/**
 * @author: Tom
 * @date: 2020-03-12 17:09
 * @description: 演示可见性带来的问题
 */
public class FieldVisibility {
    //volatile 强制每一次读取的时候，读取到的都是线程已经修改过的最新的值
    int a = 1;
    volatile int b = 2;
    //只需要在 变量b 增加 volatile
    //后面想读取b的时候就能看到b写入之前的所有操作，其中就包括a=3，所以在读取b的时候就一定能看到a的最新情况。

    private void print() {
        System.out.println("b= " + b + ", a = " + a);
    }

    private void change() {
        a = 3;
        b = a;
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }

}
