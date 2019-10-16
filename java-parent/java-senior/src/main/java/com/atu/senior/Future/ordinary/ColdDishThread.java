package com.atu.senior.Future.ordinary;

/**
 * @author ：ta0567
 * @date ：Created in 2019/9/25 22:05
 * @description：${description}
 */
public class ColdDishThread extends Thread{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("凉菜准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
