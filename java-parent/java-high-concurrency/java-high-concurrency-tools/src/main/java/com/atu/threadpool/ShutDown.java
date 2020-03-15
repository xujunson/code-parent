package com.atu.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Tom
 * @date: 2020-03-15 20:46
 * @description: 演示关系线程池
 */
public class ShutDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownTask());
        }

        Thread.sleep(1500);
        List<Runnable> runnableList = executorService.shutdownNow();


        /*System.out.println(executorService.isShutdown());
        executorService.shutdown(); //不会立即停止
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());

        Thread.sleep(10000);
        System.out.println(executorService.isTerminated());
*/
        //executorService.execute(new ShutDownTask());

        /*boolean b = executorService.awaitTermination(7L, TimeUnit.SECONDS);
        System.out.println(b);*/
    }
}

class ShutDownTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}
