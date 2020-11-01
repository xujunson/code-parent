package com.atu.thread;

public class CycleWait implements Runnable{
    private String value;
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
    }

    public static void main(String[] args) throws InterruptedException {
        CycleWait cw = new CycleWait();
        Thread t = new Thread(cw);
        t.start();
        //主线程等待方法一
//        while (cw.value == null){
//            Thread.currentThread().sleep(100);
//        }

        //方法二
        t.join();
        System.out.println("value : " + cw.value);
    }
}
