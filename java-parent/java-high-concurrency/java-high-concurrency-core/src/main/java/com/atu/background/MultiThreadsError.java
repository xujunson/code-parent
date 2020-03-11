package com.atu.background;

/**
 * @author: Tom
 * @date: 2020-03-11 14:44
 * @description: 第一种：运行结果出错
 * 演示计数不准确（减少），找出具体出错的位置。
 */
public class MultiThreadsError implements Runnable {

    static MultiThreadsError instance = new MultiThreadsError();
    int index = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上结果是" + instance.index); //小于20000，每次结果不一样
        //原因：i++写入时出错
        // 假设 i=1, 正常情况下，两个线程执行完 i++，之后 i=3；但是结果可能会发生 i=2的情况
        // 线程1拿到 i=1 之后 计算i++，但是并没有立刻写进去，导致第二个线程在拿的时候 i还是1
        // 于是把拿到的1在加1得到 i=2

    }

    @Override
    public void run() {
       /* while (index <10000) { //不能控制执行次数
            index++;
        }*/
        for (int i = 0; i < 10000; i++) {
            index++;
        }
    }
}
