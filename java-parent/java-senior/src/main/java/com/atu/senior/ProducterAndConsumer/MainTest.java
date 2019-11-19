package com.atu.senior.ProducterAndConsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ：mark
 * @date ：Created in 2019/10/24 17:41
 * @description：https://www.cnblogs.com/hankzhouAndroid/p/8693278.html
 */
public class MainTest {
    public static void main(String[] args) {
        test();
    }

    private static final long waitTime = 3000;

    private static void test() {
        Queue<Integer> queue = new LinkedList<>();// 队列对象,它就是所谓的“锁”
        int maxsize = 2;// 队列中的最大元素个数限制

        // 下面4个线程，一瞬间只能有一个线程获得该对象的锁，而进入同步代码块
        Producer producer = new Producer(queue, maxsize, "Producer");
        Consumer consumer1 = new Consumer(queue, maxsize, "Consumer1");
        Consumer consumer2 = new Consumer(queue, maxsize, "Consumer2");
        Consumer consumer3 = new Consumer(queue, maxsize, "Consumer3");

        // 其实随便先启动哪个都无所谓，因为只有一个锁，每一次只会有一个线程能持有这个锁，来操作queue
        producer.start();
        consumer2.start();
        consumer1.start();
        consumer3.start();
    }

    /**
     * 生产者线程
     */
    public static class Producer extends Thread {
        Queue<Integer> queue;// queue,对象锁
        int maxsize;// 貌似是队列的最大产量

        Producer(Queue<Integer> queue, int maxsize, String name) {
            this.queue = queue;
            this.maxsize = maxsize;
            this.setName(name);
        }

        @Override
        public void run() {
            while (true) {// 无限循环,不停生产元素，直到达到上限，只要达到上限，那就wait等待。
                synchronized (queue) {// 同步代码块,只有持有queue这个锁的对象才能访问这个代码块
                    try {
                        Thread.sleep(waitTime);
                        // sleep和wait的区别，sleep会让当前执行的线程阻塞一段时间，但是不会释放锁，
                        // 但是wait，会阻塞，并且会释放锁
                    } catch (Exception e) {
                    }

                    System.out.println(this.getName() + "获得队列的锁");// 只有你获得了queue对象的锁，你才能执行到这里
                    // 条件的判断一定要使用while而不是if
                    while (queue.size() == maxsize) {// 判断生产有没有达到上限,如果达到了上限，就让当前线程等待
                        System.out.println("队列已满，生产者" + this.getName() + "等待");
                        try {
                            queue.wait();// 让当前线程等待，直到其他线程调用notifyAll
                        } catch (Exception e) {
                        }
                    }

                    // 下面写的就是生产过程
                    int num = (int) (Math.random() * 100);
                    queue.offer(num);// 将一个int数字插入到队列中

                    System.out.println(this.getName() + "生产一个元素：" + num);
                    // 唤醒其他线程，在这里案例中是 "等待中"的消费者线程
                    queue.notifyAll();// (注：notifyAll的作用是
                    // 唤醒所有持有queue对象锁的正在等待的线程)

                    System.out.println(this.getName() + "退出一次生产过程！");
                }
            }
        }
    }

    public static class Consumer extends Thread {
        Queue<Integer> queue;
        int maxsize;

        Consumer(Queue<Integer> queue, int maxsize, String name) {
            this.queue = queue;
            this.maxsize = maxsize;
            this.setName(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {// 要想进入下面的代码，就必须先获得锁。
                    try {
                        Thread.sleep(waitTime);// sleep，让当前线程阻塞指定时长，但是并不会释放queue锁
                    } catch (Exception e) {
                    }

                    System.out.println(this.getName() + "获得队列的锁");// 拿到了锁，才能执行到这里
                    // 条件的判断一定要使用while而不是if,
                    while (queue.isEmpty()) {// while判断队列是否为空，如果为空，当前消费者线程就必须wait，等生产者先生产元素
                        // 这里，消费者有多个（因为有多个consumer线程），每一个消费者如果发现了队列空了，就会wait。
                        System.out.println("队列为空，消费者" + this.getName() + "等待");
                        try {
                            queue.wait();
                        } catch (Exception e) {
                        }
                    }

                    // 如果队列不是空，那么就弹出一个元素
                    int num = queue.poll();
                    System.out.println(this.getName() + "消费一个元素：" + num);
                    queue.notifyAll();// 然后再唤醒所有线程,唤醒不会释放自己的锁

                    System.out.println(this.getName() + "退出一次消费过程！");
                }
            }
        }
    }
}
