package com.atu.design.pattern.proxy.jdkproxy;

/**
 * @Author: Tom
 * @Date: 2022/3/16 11:35
 * @Description:
 */
public class SellHouse implements ISellHouse {
    @Override
    public void sellHouse() {
        System.out.println("买了一间房子。。。");
    }
}
