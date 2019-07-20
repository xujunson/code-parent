package com.atu.senior.Thread.run;

/**
 * @Author: xsy
 * @Date: 2019/3/13 10:07
 * @Description:
 */
public class Tortoise extends Animal {
    public Tortoise() {
        setName("乌龟");
    }

    public void runing() {
        //乌龟速度
        int dis = 2;
        length -= dis;

        System.out.println("乌龟跑了" + dis + "米，距离终点还有" + length + "米");
        if (length <= 0) {
            length = 0;
            System.out.println("乌龟获得了胜利");
            // 给回调对象赋值，让兔子不要再跑了
            if (calltoback != null) {
                calltoback.win();
            }
        }
        try {
            sleep(100);//没0.1秒跑2米
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
