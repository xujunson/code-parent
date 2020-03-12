package com.atu.background;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-12 10:29
 * @description: 构造函数中新建线程
 */
public class MultiThreadsError6 {
    private Map<String, String> states;

    public MultiThreadsError6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周四");
            }
        }).start();
    }

    //逸出————导致问题发生
    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadsError6 multiThreadsError6 = new MultiThreadsError6();
        //空指针异常，由于初始化的工作在另外一个线程中，他还没有执行完毕就会有异常。
        //Thread.sleep(1000);
        System.out.println(multiThreadsError6.getStates().get("1"));
    }
}
