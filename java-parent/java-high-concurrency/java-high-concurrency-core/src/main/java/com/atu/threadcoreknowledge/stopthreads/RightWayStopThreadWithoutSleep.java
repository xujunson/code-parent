package com.atu.threadcoreknowledge.stopthreads;

/**
 * @author: Tom
 * @date: 2020-03-09 15:58
 * @description: run()方法内没有sleep或wait方法时，停止线程
 */
public class RightWayStopThreadWithoutSleep implements Runnable {

    @Override
    public void run() {
        int num = 0;
        //Thread.currentThread().isInterrupted()判断当前线程是否中断
        while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2) {
            if (num % 10000 == 0) { //10000的倍数
                System.out.println(num + "是10000的倍数");
            }
            num++;
        }
        System.out.println("任务运行结束了");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleep());
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}