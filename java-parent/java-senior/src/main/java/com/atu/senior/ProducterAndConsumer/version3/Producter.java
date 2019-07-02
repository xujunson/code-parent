package com.atu.senior.ProducterAndConsumer.version3;

import java.util.List;
import java.util.Random;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 16:11
 * @Description : 生产者
 */


public class Producter implements Runnable {
    private List<PCData> queue;
    private int len;

    public Producter(List<PCData> queue, int len) {
        this.queue = queue;
        this.len = len;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                Random r = new Random();
                PCData data = new PCData();
                data.setData(r.nextInt(500));
                Main3.lock.lock();
                if (queue.size() >= len) {
                    Main3.empty.signalAll();
                    Main3.full.await();
                }
                Thread.sleep(1000);
                queue.add(data);
                Main3.lock.unlock();
                System.out.println("生产者ID:" + Thread.currentThread().getId() + " 生产了:" + data.getData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
