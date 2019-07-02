package com.atu.senior.Thread.ticket;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 10:03
 * @Description : 调用主类
 */
public class MainClass {
    /**
     * java多线程同步锁的使用
     * 示例：三个售票窗口同时出售10张票
     */
    public static void main(String[] args) {
        //实例化站台对象，并为每一个站台取名字
        SellTicket station1 = new SellTicket("窗口1");
        SellTicket station2 = new SellTicket("窗口2");
        SellTicket station3 = new SellTicket("窗口3");
        // 让每一个站台对象各自开始工作
        station1.start();
        station2.start();
        station3.start();
    }
}
