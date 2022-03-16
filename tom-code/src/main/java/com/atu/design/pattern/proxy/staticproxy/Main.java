package com.atu.design.pattern.proxy.staticproxy;

/**
 * @Author: Tom
 * @Date: 2022/3/16 11:28
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        //定义租房
        IRentHouse rentHouse = new RentHouse();
        //定义中介
        IRentHouse intermediary = new IntermediaryProxy(rentHouse);
        //中介租房
        intermediary.rentHouse();
    }
}