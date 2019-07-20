package com.atu.senior.Thread.communication;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:45
 * @Description: 银行账户类
 */
public class Account {
    // 默认flag 为false，要求必须先存款再取款
    private boolean flag = false;
    private int count = 0;

    /**
     * 存钱
     *
     * @param money
     */
    public void addAccount(String name, int money) {
        synchronized (this) {
            // flag false 表示可以存款，否则不可以存款
            if (flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // 存钱
                count += money;
                System.out.println(name + "...存入：" + money + "..." + Thread.currentThread().getName());
                SelectAccount(name);
                flag = true;
                this.notifyAll();
            }
        }
    }

    /**
     * 取钱
     *
     * @param money
     */
    public void subAccount(String name, int money) {
        synchronized (this) {
            if (!flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // 先判断账户现在的余额是否够取钱金额
                if (count - money < 0) {
                    System.out.println("账户余额不足！");
                    return;
                }
                // 取钱
                count -= money;
                System.out.println(name + "...取出：" + money + "..." + Thread.currentThread().getName());
                SelectAccount(name);
                flag = false;
                this.notifyAll();
            }
        }
    }

    /**
     * 查询余额
     */
    public void SelectAccount(String name) {
        System.out.println(name + "...余额：" + count);
    }
}
