package com.atu.background;


/**
 * @author: Tom
 * @date: 2020-03-12 10:17
 * @description: 观察者模式
 */
public class MultiThreadsError5 {
    int count;

    public MultiThreadsError5(MySource source) {
        //注册监听器
        source.registerListener(new EventListener() {
            //原因：注册监听器之后 在onEvent方法中就已经隐含的暴露了外部类的对象
            //在 new EventListener() 匿名内部类中持有对外部类的引用，所以在这可以直接对count进行打印和修改
            @Override
            public void onEvent(Event e) {
                //打印的为0，而不是100
                System.out.println("\n我得到的数字是 " + count);
            }
        });
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //子线程10毫秒之后触发事件
                mySource.eventCome(new Event() {
                });
            }
        }).start();

        MultiThreadsError5 multiThreadsError5 = new MultiThreadsError5(mySource);
    }

    static class MySource {

        private EventListener listener;

        //注册监听器
        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }

        //一旦事件发生，监听器就会触发对应事件
        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }

    }

    interface EventListener {

        void onEvent(Event e);
    }

    interface Event {

    }
}
