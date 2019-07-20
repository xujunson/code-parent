package com.atu.senior.Thread.station;

/**
 * @Author: xsy
 * @Date: 2019/3/12 16:52
 * @Description: 创建一个站台类，继承Thread
 */
public class Station extends Thread {

    // 通过构造方法给线程名字赋值
    public Station(String name) {
        super(name);
    }

    // 为了保持票数的一致，票数要静态
    static int ticket = 20;

    // 创建一个静态钥匙,值是任意的
    static Object object = "aa";

    // 重写run方法，实现买票操作

    @Override
    public void run() {
        while (ticket > 0) {
            // 这个很重要，必须使用一个锁，
            // 进去的人会把钥匙拿在手上，出来后才把钥匙拿让出来
            // synchronized就具有使每个线程依次排队操作共享变量的功能
            synchronized (object) {
                if (ticket > 0) {
                    System.out.println(getName() + "sell: " + ticket + "");
                    ticket--;
                } else {
                    System.out.println("Over!");
                }

                try {
                    sleep(1000);//休息一秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(ticket+"------------------------------------1");
        }
    }
}
