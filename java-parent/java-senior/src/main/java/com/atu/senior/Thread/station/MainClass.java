package com.atu.senior.Thread.station;

/**
 * @Author: xsy
 * @Date: 2019/3/12 16:56
 * @Description: 主测试类
 */
public class MainClass {
    public static void main(String[] args) {
        //实例化站台对象，并为每一个站台取名字
        Station station1 = new Station("Win1 ");
        Station station2 = new Station("Win2 ");
        Station station3 = new Station("Win3 ");

        // 让每一个站台对象各自开始工作
        station1.start();
        station2.start();
        station3.start();
    }
}
