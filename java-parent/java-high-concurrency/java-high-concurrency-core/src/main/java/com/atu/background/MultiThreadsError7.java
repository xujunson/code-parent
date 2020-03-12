package com.atu.background;

/**
 * @author: Tom
 * @date: 2020-03-12 10:41
 * @description: 用工厂模式 对监听器模式的修复
 */
public class MultiThreadsError7 {
    int count;

    private EventListener listener;

    //设置构造方法为private
    private MultiThreadsError7(MultiThreadsError7.MySource source) {
        //注册监听器
        listener = new EventListener() {
            @Override
            public void onEvent(MultiThreadsError7.Event e) {
                //打印的为0，而不是100
                System.out.println("\n我得到的数字是 " + count);
            }
        };
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static MultiThreadsError7 getInstance(MySource source) {
        //先初始化
        MultiThreadsError7 safeListener = new MultiThreadsError7(source);
        //在注册
        source.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MultiThreadsError7.MySource mySource = new MultiThreadsError7.MySource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //子线程10毫秒之后触发事件
                mySource.eventCome(new MultiThreadsError7.Event() {
                });
            }
        }).start();

        MultiThreadsError7.getInstance(mySource);
    }

    static class MySource {

        private MultiThreadsError7.EventListener listener;

        //注册监听器
        void registerListener(MultiThreadsError7.EventListener eventListener) {
            this.listener = eventListener;
        }

        //一旦事件发生，监听器就会触发对应事件
        void eventCome(MultiThreadsError7.Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }

    }

    interface EventListener {

        void onEvent(MultiThreadsError7.Event e);
    }

    interface Event {

    }
}
