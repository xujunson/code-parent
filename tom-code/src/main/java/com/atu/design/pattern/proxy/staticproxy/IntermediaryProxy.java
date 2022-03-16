package com.atu.design.pattern.proxy.staticproxy;


/**
 * @Author: Tom
 * @Date: 2022/3/16 11:27
 * @Description:
 */
public class IntermediaryProxy implements IRentHouse {

    private IRentHouse rentHouse;

    public IntermediaryProxy(IRentHouse irentHouse){
        rentHouse = irentHouse;
    }

    @Override
    public void rentHouse() {
        System.out.println("交中介费");
        rentHouse.rentHouse();
        System.out.println("中介负责维修管理");
    }
}