package com.atu.senior.ProducterAndConsumer.version3;

import java.util.List;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 16:10
 * @Description :
 */
public class Consumer implements Runnable {
    private List<PCData> queue;

    public Consumer(List<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                PCData data = null;
                Main3.lock.lock();
                if (queue.size() == 0) {
                    Main3.full.signalAll();
                    Main3.empty.await();
                }
                Thread.sleep(1000);
                data = queue.remove(0);
                Main3.lock.unlock();
                System.out.println("消费者ID:" + Thread.currentThread().getId() + " 消费了:" + data.getData() + " result:" + (data.getData() * data.getData()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
