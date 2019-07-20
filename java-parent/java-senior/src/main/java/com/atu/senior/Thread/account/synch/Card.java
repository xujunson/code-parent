package com.atu.senior.Thread.account.synch;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:46
 * @Description: 银行卡负责存钱
 */
public class Card implements Runnable {
    private String name;
    private AccountSynch account;

    public Card(String name, AccountSynch account) {
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
            account.addAccount(name, 100);
        }
    }

}
