package com.atu.senior.Thread.communication;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:46
 * @Description: 银行卡负责存钱
 */
public class Card implements Runnable {
    private String name;
    private Account account;

    public Card(String name, Account account) {
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
