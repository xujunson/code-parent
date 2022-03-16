package com.atu.design.pattern.proxy.staticproxy;

/**
 * @Author: Tom
 * @Date: 2022/3/16 11:27
 * @Description: 租房的实现类
 */
public class RentHouse implements IRentHouse {
    @Override
    public void rentHouse() {
        System.out.println("租了一间房子。。。");
    }
}