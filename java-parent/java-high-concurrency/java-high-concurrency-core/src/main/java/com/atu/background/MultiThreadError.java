package com.atu.background;

/**
 * @author: Tom
 * @date: 2020-03-11 23:55
 * @description: 死锁的原因分析
 */
public class MultiThreadError implements Runnable {
    int flag = 1; //标记位，提供给两个线程使用
    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        MultiThreadError r1 = new MultiThreadError();
        MultiThreadError r2 = new MultiThreadError();
        //两个线程取到不同的锁，而且这两个锁相互等待————实现死锁
        r1.flag = 1;
        r2.flag = 0;
        new Thread(r1).start();
        new Thread(r2).start();
    }

    //结果：flag = 0，flag = 1
    //分析原因：由于线程1走到 " synchronized (o1) {}"这边，去拿" synchronized (o2)"时，
    // 而"synchronized (o2)"已经被线程2所拿到，所以，线程1在"synchronized (o2)————36"处等待，而线程2会在"synchronized (o1)————49" 处等待。
    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("0");
                }
            }
        }
    }
}
