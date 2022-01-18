package com.atu.senior.delay;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Tom
 * @Date: 2021/10/24 10:07
 * @Description:
 */
public class HashedWheelTimerTest {
    private static final long start = System.currentTimeMillis();

    public static void main(String[] args) {

        // 初始化netty时间轮
        HashedWheelTimer timer = new HashedWheelTimer(1, // 时间间隔
                TimeUnit.SECONDS,
                10); // 时间轮中的槽数

        TimerTask task1 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("已经过了" + costTime() + " 秒，task1 开始执行");
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("已经过了" + costTime() + " 秒，task2 开始执行");
            }
        };

        TimerTask task3 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("已经过了" + costTime() + " 秒，task3 开始执行");
            }
        };

        // 将任务添加到延迟队列
        timer.newTimeout(task1, 0, TimeUnit.SECONDS);
        timer.newTimeout(task2, 3, TimeUnit.SECONDS);
        timer.newTimeout(task3, 15, TimeUnit.SECONDS);
    }

    private static Long costTime() {
        return (System.currentTimeMillis() - start) / 1000;
    }
}
