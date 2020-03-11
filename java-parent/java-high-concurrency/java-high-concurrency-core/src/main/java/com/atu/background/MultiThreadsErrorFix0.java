package com.atu.background;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Tom
 * @date: 2020-03-11 14:44
 * @description: 第一种：运行结果出错
 * 演示计数不准确（减少），找出具体出错的位置。
 */
public class MultiThreadsErrorFix0 implements Runnable {

    static MultiThreadsErrorFix0 instance = new MultiThreadsErrorFix0();
    int index = 0;
    //原子变量-原子整形-CAS-线程安全
    static AtomicInteger realIndex = new AtomicInteger(); //真正数量
    static AtomicInteger wrongCount = new AtomicInteger(); //出错数量


    final boolean[] marked = new boolean[10000000];

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上结果是" + instance.index); //表面上结果是19974
        System.out.println("真正运行的次数" + realIndex.get()); //真正运行的次数20000
        System.out.println("错误次数" + wrongCount.get()); //错误次数22

    }

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0; i < 10000; i++) {

            index++;
            realIndex.incrementAndGet();

            //结果还是对不上
            //原因：假设线程1进入此处，判断为false，然后准备执行 "marked[index] = true;" 之前，
            // 线程2也进入此处，此处还是false，第二个线程也跳过监测机制，所以就没办法捕捉。
            /*if (marked[index]) { //解决1 检查是否已经标记过了
                System.out.println("发生错误" + index);
                wrongCount.incrementAndGet();
            }
            marked[index] = true;*/

            //解决2 加入同步锁
            //结果：
            //表面上结果是20000
            //真正运行的次数20000
            //错误次数781

            //分析原因：假设两个线程都执行完前面两行代码，到此处拿锁；
            //假设线程1拿到这把锁，于是线程1执行下方代码，假设index最开始是0，且发生冲突
            //所以对于两个线程而言 index都是1，于是线程1进来之后marked[1] = true;
            //线程2进来发现 marked[1]已经是true了，于是就统计到这次错误；

            //但是有一个问题，假设线程2执行到if语句时，CPU把线程1切回去，让线程1执行，线程1又会执行 index++;
            //原来index=1，现在index=2，入过此时线程在切回到线程2，if语句就会判断marked[2]，此时的index是不准确的，它可能会大于我们实际想检查的那个值
            synchronized (instance) {
                if (marked[index]) { //检查是否已经标记过了
                    System.out.println("发生错误" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }
}
