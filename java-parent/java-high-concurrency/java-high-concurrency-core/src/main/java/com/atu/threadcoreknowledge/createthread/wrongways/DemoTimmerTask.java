package com.atu.threadcoreknowledge.createthread.wrongways;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: Tom
 * @date: 2020-03-09 10:41
 * @description: 定时器创建线程
 */
public class DemoTimmerTask {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },1000,1000);
    }
}
