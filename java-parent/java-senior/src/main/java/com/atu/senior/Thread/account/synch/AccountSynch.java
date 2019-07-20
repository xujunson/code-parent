package com.atu.senior.Thread.account.synch;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:45
 * @Description: 银行账户类
 */
public class AccountSynch {
    private int count = 0;

    /**
     * 存钱
     *
     * @param money
     */
    public void addAccount(String name, int money) {
        synchronized (this) {
            // 存钱
            count += money;
            System.out.println(name + "...存入：" + money + "..." + Thread.currentThread().getName());
            SelectAccount(name);
        }
    }

    /**
     * 取钱
     *
     * @param money
     */
    public void subAccount(String name, int money) {
        synchronized (this) {
            // 先判断账户现在的余额是否够取钱金额
            if (count - money < 0) {
                System.out.println("账户余额不足！");
                return;
            }
            // 取钱
            count -= money;
            System.out.println(name + "...取出：" + money + "..." + Thread.currentThread().getName());
            SelectAccount(name);
        }
    }

    /**
     * 查询余额
     */
    public void SelectAccount(String name) {
        System.out.println(name + "...余额：" + count);
    }
}
