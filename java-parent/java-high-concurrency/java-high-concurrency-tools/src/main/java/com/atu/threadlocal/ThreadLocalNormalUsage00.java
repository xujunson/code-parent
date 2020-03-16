package com.atu.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Tom
 * @date: 2020-03-16 9:21
 * @description: 用两个线程分别打印出生日信息
 */
public class ThreadLocalNormalUsage00 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(10);
                System.out.println(date);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(1007);
                System.out.println(date);
            }
        }).start();
    }

    public String date(int seconds) {

        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
