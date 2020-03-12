package com.atu.background;

/**
 * @author: Tom
 * @date: 2020-03-12 10:08
 * @description: 初始化未完毕，就this赋值
 */
public class MultiThreadsError4 {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
        //Thread.sleep(10); //1, 0
        Thread.sleep(105); //1, 1
        if (point != null) {
            System.out.println(point);
        }
    }

}

class Point {
    private final int x, y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        //x y 赋值有先有后，由于过早的发布point 导致看到的x y在不同时刻是不一样的。
        MultiThreadsError4.point = this;
        Thread.sleep(100);
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}

class PointMaker extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
