package com.atu.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Tom
 * @date: 2020-03-18 13:34
 * @description: 演示高并发场景下，LongAdder比AtomicLong性能好
 */
public class AtomicLongDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicLong counter = new AtomicLong(0);

        //创建线程池
        ExecutorService service = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            //提交任务
            service.submit(new Task(counter));
        }

        service.shutdown();
        //service.isTerminated() 判断线程池是否把任务都执行完毕
        while (!service.isTerminated()) {

        }
        long end = System.currentTimeMillis();
        System.out.println(counter.get());
        System.out.println("AtomicLong耗时：" + (end - start));
    }

    private static class Task implements Runnable {

        //成员变量
        private AtomicLong counter;

        public Task(AtomicLong counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                //循环1w次，自增
                counter.incrementAndGet();
            }
        }
    }

}
