package com.atu.design.pattern.template.general;

/**
 * @author: Tom
 * @create: 2023-01-30 16:53
 * @Description: 存款
 */
public class SaveMoneyHandler extends AbstractBusinessHandler {
    @Override
    public boolean isVip() {
        return true;
    }

    @Override
    public void handle() {
        System.out.println("save 1000");
    }

    public static void main(String[] args) {
        SaveMoneyHandler saveMoneyHandler = new SaveMoneyHandler();
        saveMoneyHandler.execute();
    }
}
