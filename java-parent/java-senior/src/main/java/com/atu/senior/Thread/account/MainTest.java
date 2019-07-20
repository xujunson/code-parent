package com.atu.senior.Thread.account;

/**
 * @Author: xsy
 * @Date: 2019/3/13 8:48
 * @Description: 主测试类
 */
public class MainTest {
    public static void main(String[] args) {

        // 1、同步---开个银行帐号
        Account account = new Account();
        // 开银行帐号之后银行给张银行卡
        Card card = new Card("card", account);
        // 开银行帐号之后银行给张存折
        Paper paper = new Paper("存折", account);

        Thread thread1 = new Thread(card);
        Thread thread2 = new Thread(paper);

        thread1.start();
        thread2.start();
    }
}
