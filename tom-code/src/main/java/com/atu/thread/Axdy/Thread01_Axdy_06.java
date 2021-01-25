package com.atu.thread.Axdy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author: Tom
 * @date: 2021-01-25 14:09
 * @description: 阻塞队列
 */
public class Thread01_Axdy_06 {
    BlockingQueue<String> blockingQueue12, blockingQueue23;

    public Thread01_Axdy_06() {
        blockingQueue12 = new SynchronousQueue<String>();
        blockingQueue23 = new SynchronousQueue<String>();
    }

    /**
     * 阻塞功能：最有特色的两个带有阻塞功能的方法是：take和put。
     * take()方法：获取并移除队列的头结点，一旦执行take的时候，队列里无数据，则阻塞，直到队列里有数据；
     * put()方法：插入元素。但是如果队列已满，那么就无法继续插入，则阻塞，直到队列里有了空闲空间；
     *
     * @param printFirst
     * @throws InterruptedException
     */
    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        blockingQueue12.put("stop");
    }

    public void second(Runnable printSecond) throws InterruptedException {
        blockingQueue12.take();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        blockingQueue23.put("stop");
    }

    public void third(Runnable printThird) throws InterruptedException {
        blockingQueue23.take();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    public static void main(String[] args) {
        final Thread01_Axdy_06 axdy_03 = new Thread01_Axdy_06();


        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_03.third(new Runnable() {
                        public void run() {
                            System.out.println("third");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_03.first(new Runnable() {
                        public void run() {
                            System.out.println("first");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    axdy_03.second(new Runnable() {
                        public void run() {
                            System.out.println("second");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
