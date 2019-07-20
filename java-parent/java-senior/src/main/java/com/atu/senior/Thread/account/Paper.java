package com.atu.senior.Thread.account;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:47
 * @Description: 存折负责取钱
 */
public class Paper implements Runnable {
    private String name;
    private Account account;

    public Paper(String name, Account account) {
        this.account = account;
        this.name = name;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.subAccount(name, 50);
        }

    }

}
