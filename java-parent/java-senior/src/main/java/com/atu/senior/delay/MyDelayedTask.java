package com.atu.senior.delay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Tom
 * @Date: 2021/10/15 10:03 上午
 * @Description:
 */
public class MyDelayedTask implements Delayed {

    private String orderId;
    private long startTime;
    private long delayMillis;

    public MyDelayedTask(String orderId, long delayMillis) {
        this.orderId = orderId;
        this.startTime = System.currentTimeMillis();
        this.delayMillis = delayMillis;
    }

    /**
     * 获得延迟时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((startTime + delayMillis) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 比较排序：当前时间的延迟时间 - 比较对象的延迟时间
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    public void exec() {
        System.out.println(orderId + "编号的订单要删除啦！！！");
    }

    private static DelayQueue<MyDelayedTask> delayQueue = new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<String>();

        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");

        long start = System.currentTimeMillis();

        for (int i = 0; i < list.size(); i++) {
            //延迟 3s
            delayQueue.put(new MyDelayedTask(list.get(i), 3000));
            delayQueue.take().exec();

            System.out.println("After " + (System.currentTimeMillis() - start) + " MilliSeconds");
        }
    }
}
