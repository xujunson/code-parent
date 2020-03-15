package com.atu.threadpool;

/**
 * @author: Tom
 * @date: 2020-03-15 11:23
 * @description:
 */
public class ForLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new EveryTaskOneThread.Task());
            thread.start();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
