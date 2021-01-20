package com.atu.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @author: Tom
 * @date: 2021-01-14 16:33
 * @description:
 */
public class InitValueInThreadLocal {
    private static final StringBuilder INIT_VALUE = new StringBuilder("init");

    //覆写ThreadLocal的initialValue，返回StringBuilder静态引用
    private static final ThreadLocal<StringBuilder> builder = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return INIT_VALUE;
        }
    };

    private static class AppendStringThread extends Thread {
        @Override
        public void run() {
            StringBuilder inThread = builder.get();
            for (int i = 0; i < 10; i++) {
                inThread.append("-" + i);
            }
            System.out.println(inThread.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new AppendStringThread().start();
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
