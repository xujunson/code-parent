package com.atu.senior.thread.bank;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 10:06
 * @Description :
 */

public class PersonB extends Thread {
    Bank bank;

    String mode;

    public PersonB(Bank bank, String mode) {
        this.bank = bank;
        this.mode = mode;
    }

    public void run() {
        while (bank.money >= 200) {
            try {
                bank.outMoney(200, mode);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

