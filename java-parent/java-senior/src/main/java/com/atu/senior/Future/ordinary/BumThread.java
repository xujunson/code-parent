package com.atu.senior.Future.ordinary;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/25 22:05
 * @description：${description}
 */
public class BumThread extends Thread{

    @Override
    public void run() {
        try {
            Thread.sleep(1000*3);
            System.out.println("包子准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
