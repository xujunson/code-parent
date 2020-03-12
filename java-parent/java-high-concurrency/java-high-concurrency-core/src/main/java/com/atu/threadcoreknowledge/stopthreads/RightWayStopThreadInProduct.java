package com.atu.threadcoreknowledge.stopthreads;

/**
 * @author: Tom
 * @date: 2020-03-09 16:39
 * @description: 最佳实践：catch了 InterruptedException之后的优先选择：在方法签名中抛出异常
 * 那么在run()就会强制 try/catch
 */
public class RightWayStopThreadInProduct implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("go");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                System.out.println("保存日志");
                e.printStackTrace();
            }
        }
    }

    //错误方式
    /*private void throwInMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProduct());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
